# pins

## How long did it take you to complete the project?
I spent about 13 hours coding that project


## What part of this project did you find most challenging?
The most challenging parts for me were:
- Working with an API without any documentation about it, as it would be with any API with no documentation
- Moshi ended up not being that easy to set up
- Using Compose to fully develop the UI of the app
- Date formatting and parsing in Java is always interesting


## If you used 3rd party libraries, explain for each one the reason for introducing that dependency and how you selected that particular library.
- Moshi to parse Json payload
- Retrofit to handle API calls
- Firebase to track Performance issues
- GoogleMap for the map

I selected Moshi and Retrofit because there are two libraries coming from the same company and well known among the Android community.
They work very well together and are still highly maintained.

Firebase is almost unavoidable for Android apps nowadays. 
It's a rich solution that gathers a lot of different services that are easy to use and to setup.
In the current state of the project it's barely used, but could bring it to a whole new level with more time spent on it.

## Given 8 more hours, how would you improve your project?
I would improve the UI/UX in many ways:
- a loading state while loading the data
- a selected state for the pin
- the current bottom sheet layout is quite simple and could be improved
- the content description should not be static but localized
- add a pin clustering approach on the map

Also I would love to look into a better implementation possibly of the redux approach in Kotlin with Compose.