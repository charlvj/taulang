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

# Current State
The following features are implemented:
1) Everything is a Function or a Value, depending on the context.
2) Values are lazy - only realized once needed.
3) You can create custom functions (to).
4) Lambdas are supported.
5) You can set a "variable", which is really just a named function to a value.
6) Error handling.
