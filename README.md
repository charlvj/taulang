# taulang
Language inspired by Logo.
I created it because I wanted to teach my son some programming with Logo, but the free version I was using kept crashing. I first created the language itself and called it Tau. I created a different project that creates a Logo adapter to the language.

# Overview
TauLang is a functional language inspired by the Logo language.

```
to :introduce_tau [:to_whom]
  [ printline [ "Hello " 
                ifnull to_whom "Anonymous Person" 
                ", I am Tau." ]
  ]
introduce_tau "Curious Reader"  /* --> Hello Curious Reader, I am Tau.   */
introduce_tau ?                 /* --> Hello Anonymous Person, I am Tau. */
```

# Overview

## Syntax
A Tau program is a sequence of functions separated by spaces. That said, the functions may appear differently:
1) An identifier is the name of a function to call.
2) A literal is a essentially a _Value_ that is converted to a function internally. Tau supports the following literals:
   * Numbers: 1, 2, 3.9, -93.12
   * Strings: "blah", ""
   * Symbols: :function_name, :or_something
   * Null: ?
3) Square brackets - [ and ]. Anything enclosed by square brackets becomes a single value: list. Function inside a list is not executed until the list is _call_ ed.
4) Comments. Comments are anything enclosed by /* ... */ and are ignored by the interpreter.


## Functions
Everything in Tau is stored in memory as a function. As a consequence, everything "returns" a value. A list of functions can form a new function, the return value of which is the return value of the last function in the list. A Tau program is essentially a function that returns a value like any other function, but there are other ways to define a function:
1) A list. Any list can be called using the _call_ function. Example:
```
call [] [ printline "Hello" printline "world" ]
```
2) Using the _to_ function. The _to_ function takes three parameters: first a symbol for the name of the function, second a list of parameters the function you are defining can take, and third a list containing the sequence of functions that must be executed when this new function is called. A function defined in this manner can be called by simply stating the function name plus the required parameters. Example:
```
to :greet_me [:my_name] [ print "Hello " printline my_name ]
greet_me "Buddy"
```
3) Using the _lambda_ function to define a new anonymous function with named parameters. Lambdas are also called with the _call_ function. Example:
```
call lambda [:name] [ printline [ "Hello " name ] ] ["Llama"]
```

## Values
A function returns a value. Tau supports the following value types:
1) Number: any whole or decimal number. Examples include 1, 2, 4.3, -9.243
2) String: anything enclosed with quotes. Examples include "blah", ""
3) Logical: _true_ of _false_.
4) List: a list of tokens enclosed in square brackets. Examples include [1 2 3], [2 "hello" ["a" "nested" "list"]]
5) Null: denotes no specific value. It is represented by ?.
6) Symbol: used to identify a function without evaluating it. It is any identifier prefixed by a colon. Examples include :my_function, :your_function.
7) Function: This is essentially an anonymous function, created by the lambda function.
8) Error: This is a special value that has some information about an error, but also causes the execution of a function to stop immediately, returning the error. This can create a chain reaction that will last until the error is handled and a non-error can be returned.

## Summary
That is pretty much it. The language is rather simple, with a lot of potential.

# Current State
The following features are implemented:
1) Everything is a Function or a Value, depending on the context.
2) Values are lazy - only realized once needed.
3) You can create custom functions (to).
4) Lambdas are supported.
5) You can set a "variable", which is really just a named function to a value.
6) Error handling.
