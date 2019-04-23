package norswap.autumn.naive

import norswap.autumn.Grammar
import norswap.autumn.parsers.*

// ---------------------------------------------------------------------------------------------

/**
 * Matches [p], enforcing the transactionality rule (either suceeds or fails with no side-effects).
 * Hence, [p] is absolved from having to respect the transactionality rule itself.
 */
class Transact(val p: Parser) : Parser() {
    override fun invoke() = grammar.transact(p)
}

// ---------------------------------------------------------------------------------------------

/**
 * Matches [p], discarding any errors that may have been recorded during its invocation.
 */
class IgnoreErrors(val p: Parser) : Parser() {
    override fun invoke() = grammar.ignore_errors(p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches [p]. If [p] fails, discards any failure that may have been recorded during its execution.
 */
class IgnoreErrorsIfSuccessful(val p: Parser) : Parser() {
    override fun invoke() = grammar.ignore_errors_if_successful(p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Runs [f] then succeed.
 */
class Perform(val f: Grammar.() -> Unit) : Parser() {
    override fun invoke() = grammar.perform(f)
}

// -------------------------------------------------------------------------------------------------

/**
 * Prints the current input position and [str], then succeed.
 */
class Log(val str: String) : Parser() {
    override fun invoke() = grammar.log(str)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches [p]. If [p] fails, discards any failure that may have been recorded during its execution,
 * and registers a failure with the message generated by [failure] instead.
 */
class Contain(val failure: () -> String, val p: Parser) : Parser() {
    override fun invoke() = grammar.contain(failure, p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Equivalent to `contain(failure) { transact(p) }` ([contain], [transact]).
 */
class TransactContain(val failure: () -> String, val p: Parser) : Parser() {
    override fun invoke() = grammar.transact_contain(failure, p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches [p], catching any exception it might throws.
 *
 * This parser fails if it catches an exception which uses the input position at the time
 * the exception is thrown. If the caught exception is an
 * [AutumnLogicException], registers the failure it carries, otherwise registers a
 * [CaughtException].
 */
class Catch(val p: Parser) : Parser() {
    override fun invoke() = grammar.catch(p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches [p], catching any exception it might throws. If [p] throws an exception, discards any
 * failure that may have been recorded during its execution, and registers an exception in the
 * same manner as [catch] instead.
 */
class CatchContain(val p: Parser) : Parser() {
    override fun invoke() = grammar.catch_contain(p)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches input using [gather] then, if successful, calls [refine] with the matching input
 * and use the result as the result of the parse.
 */
class Inner(val gather: Parser, val refine: (String) -> Boolean) : Parser() {
    override fun invoke() = grammar.inner(gather, refine)
}

// -------------------------------------------------------------------------------------------------

/**
 * Matches all characters until [terminator] (also matched).
 *
 * Then, if successful, all characters matched in this manner (excluding [terminator]) are collected
 * in a string, which is passed to [refine], whose result is the result of the parse.
 *
 * The exclusion of the terminator is what makes this different from [inner] and closer
 * to [gobble].
 */
class UntilInner(val terminator: Parser, val refine: (String) -> Boolean) : Parser() {
    override fun invoke() = grammar.until_inner(terminator, refine)
}

// -------------------------------------------------------------------------------------------------

/*
 * A parser that matches the same thing as parsing [sub_grammar] with the remainder of the input
 * would. If successful, the [completion] function is called, passing it [sub_grammar].
 *
 * The default action for the completion function is to push the top of the value stack of the
 * sub-grammar on top on the value stack of the current grammar.
 */
class SubGrammar(
        g: Grammar,
        val sub_grammar: Grammar,
        completion: Grammar.(Grammar) -> Unit = { stack.push(it.stack[0]) })
    : Parser() {
    init {
        grammar = g
    }

    val delegate = g.sub_grammar(sub_grammar, completion)
    override fun invoke() = delegate()
}

// -------------------------------------------------------------------------------------------------

// NOTE: We eschew porting `sub_grammar_inner` for now.
// The problem is that it is not a parser subclass, so we can't track its sub-parsers easily.

// -------------------------------------------------------------------------------------------------