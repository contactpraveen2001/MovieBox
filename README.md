# Movie Box
The is the Moview box app. Now check the complete list of now playing movies and most popular movies. Also, see the complete detail of the movie. 

## Approach

- Use Glid for image caching.  
- For the rating widget, I used a ring shape as a progressDrawable and update the tint colour based on the rating.  

## Assumptions 

- The popular movies API does not return the runtime so the duration option is not present in the popular movie list. 
- Rating is considered only up to the first decimal point. 
- The movie box title image asset was just returning the yellow file without the text. So used the TextView with the MovieBox as text. 
- I feel like MovieBox should be the app’s name, so I used the same for the app name.
- Long movie titles will wrap and show the complete name in the second line in the list screen.
- I was not able to find the Helvetica Neue font in the assets so download one.

## Libraries

- Glid: For image loading and caching.
- Paging: For paging for the list screen.
- Hilt: For dependency injection.
