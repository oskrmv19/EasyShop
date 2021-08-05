Test implementation - MELI

## Libreries and components: ##
  - ViewModel with LiveData
  - Retrofit ( To consume services)
  - Room ( Local Database)
  - Hilt ( Dependency Injection)
  - Navigation component and Safe Args
  - Coroutines with Flow
  - Timber for logging
  - Gson to parse service response
  - Glide to load images

## Project architecture ##

- Language: Kotlin
- Desing pattern: MVVM
- Clean architecture
  - Data Layer
  - Domain layer
  - Presentation layer
- SOLID Principles

## Directories ##
- App:
- Core # With the base architecture and commom components and utilities
- Screens # Features or Screen of the App like: Search, Favorites, Categorires, Detail, Last products seen
 
Every Features or Screen has data, domain and/or presentation layer (Just if are strictly necessary)
 - Data # Data layer contains:
	- db # Room Database, Entities, DataConverters
	- mappers # To transform Entities to DTO and viceverse
	- network # To handle the network connection state
	- repository # Implementation of repository interfaces of Domain layer, handler data flow and control exceptions
	- service # Definition of retrofit interface to consume services
	- provider # To handler intents like Intent.ACTION_VIEW, Intent.ACTION_SEARCH

- Domain # Domain layer contains:
	- data classes or models # similar to java's DTO
	- Failure # definition of Failures to handle internal state of data
	- network # Interface definitions for Network connection handler
	- repository # Interface repository definitions
	- request # to create headers, params and body request to consume services
	- states # To handler states between viewmodels and UI components like Activities, Fragments, etc.
	- usecase # Definition of use cases of the App, with the idea of single responsability principle

 - presentation # Presentation layer contains:
	- extensions # Kotlin extensions function to facilite programming logic
	- activities, adapters, fragments, listeners, viewmodels and utility's classes
	
- App.kt	# To setting the app modules or components
- MainActivity.kt # To Set Android Navigation Component, Drawer Menu and handle the intents like Intent.ACTION_VIEW, Intent.ACTION_SEARCH

## Notes: ##
	- Unit tests left (I usually use Mockk, Robolectric and Junit) 
	- Ofuscation Left
	- Handle all services states code error (Just the more commoms)

