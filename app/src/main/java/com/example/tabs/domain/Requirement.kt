package com.example.tabs.domain

import com.example.tabs.domain.formatting.Formatting
import com.example.tabs.domain.validation.Validation

/**
 * Contains the result value after requirement execution.
 * Can contain an error which
 */
data class RequirementResult<T>(
  val value: T,
  val error: Validation<*>?
)

class Requirement<P, F>(
  private val formatting: Formatting<P, F>,
  private val validity: List<Validation<P>>
) {
  
  /**
   * Based on the passed [formatting] and [validity] rules, it strips the input to its plain form,
   * then runs it against the validation rules and stops on the first detected failure.
   * Then it gets formatted back and accompanied with any found validation rule break
   * it is returned as part of [RequirementResult].
   */
  fun check(input: F): RequirementResult<F> =
    formatting.strip(input).map { plainValue ->
      RequirementResult(
        value = formatting.apply(plainValue),
        error = validity.find { rule -> !rule.isValid(plainValue) }
      )
    }
}

fun <A, B> A.map(block: (A) -> B): B = block(this)