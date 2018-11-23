# KNEL - Kotlin NonEmptyList

Implementation of NonEmptyList - List with at least 1 element

NonEmptyList implements kotlin.List interface and can be used anywhere where you use kotlin.List or java.lang.List

## Why do I need this?

Sometimes you have code that expects list with at least one element and 
empty list considered as illegal argument.

Problem that you cannot force user of your API to use only non empty lists using Kotlin (or Java/JS/C) type system.

Possible solution: introduce special type that represents list with at least one element.
So now compiler can check correct usage of your API and no need to check.

This library provides such type: [NonEmptyList](src/commonMain/kotlin/ru/gildor/knel/NonEmptyList.kt)

`NonEmptyList` is subtype of `kotlin.List` and can be used as any other list. 

But if you use `NonEmptyList` in your API you force use of this API to use `NonEmptyList` so non need


`NonEmptyList<T>` interface contains two additional properties:
`head: T` - first element of a list. Can be null if generic type of NonEmptyList is nullable. 
Use this property instead of `first()` or `get[0]` because it's always safe to use `head`.
`tail: List<T>` - all elements of a list excluding first one. 
                  Cannot be null, but can be empty (for list with only one element)  
                  
## Builder functions

You have a few ways to create NonEmptyList instance.

Implementations of NonEmptyList are private, but you can use these builder functions: 

`nelOf(head: T)` and `NonEmptyList(head: T)` - instance with 1 element. 
Implementation is optimized and uses single element list implementation under the hood.

`nelOf(head: T, vararg tail: T): NonEmptyListT>` - creates instance from head and array of varargs

`NonEmptyList(head: T, tail: List<T>)` - creates instance from head and tail list. 
For tail will be created defencive copy on creation 

`List<T>.toNel(): NonEmptyList<T>?` - converts existing list to NonEmptyList. Returns null if original list is empty

`List<T>.toNelUnsafe(): NonEmptyList<T>` - convert existing list to NonEmptyList. 
Throws `NoSuchElementException` if original list is empty. 
Use this builder only as assert and try to use other builders if it's possible. 

## Typealias

Type alias `Nel` is available for `NonEmptyList`.
 
It's relatively common abbreviation that allows to define type in much more short way.

But there is no builder functions for `Nel` like for `NonEmptyList`, 
so use `NonEmptyList` or `nelOf` instead.

If you think that such inconsistency of API is something bad, be free to open issue it can be reconsidered.

## Mutability

Interface and all implementations of `NonEmptyList` are read-only, 
but for simplicity and performance we don't create defencive copy of `tail` on read, 
so on JVM `NonEmptyList` is not really immutable and as all Kotlin collections just read-only. 

If you need real immutable list, create NonEmptyList using builder function:

```
NonEmptyList(head: T, tail: List<T>)
```

and pass real immutable implementation to tail param.

On JVM you can use `java.util.Collections.unmodifiableList`

## Alternatives

[Arrow](https://arrow-kt.io/) - functional library for Kotlin has [own implementation of NonEmptyList](https://arrow-kt.io/docs/datatypes/nonemptylist/).

But this implementation is not subtype of `List` and provide more functional approach.

If you already use `Arrow` on your project just keep using it. 

But if you just want to make your code little bit more type safe and do not use Arrow, you can use this library.

## License

    Copyright 2018 Andrey Mischenko 
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

