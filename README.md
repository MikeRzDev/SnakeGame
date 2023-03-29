Snake Game

A basic snake game that is played by Heuristic rules, click start to indefinitely play the game.
when stop button is pressed the score is recorded. these scores could be seen in the scores feature.
It also allows the user to customize name and color of both snakes. 
As a bonus it reproduces sound effects :).

on the presentation layer there is a mix between Jetpack Compose and traditional layouts.
as a lot of projects still use old fashioned layouts, i wanted to demonstrate how to handle both.

This game was made using some Android Jetpack libraries:

- Hilt Dependency Injection
- Jetpack Compose
- Jetpack DataStore
- Flow
- Coroutines
- ViewModel
- LiveData

Its architecture is based on Clean Architecture and MVVM with the three basic layers 
(because of time constraints not all concepts were applied):

- Presentation
- Domain
- Data

It also includes a pair of unit tests written with JUnit and Mockk to demonstrate 
my style of writing tests.


[![Watch the video](https://img.youtube.com/vi/dln3cg5OTyk/maxresdefault.jpg)](https://www.youtube.com/watch?v=dln3cg5OTyk)
