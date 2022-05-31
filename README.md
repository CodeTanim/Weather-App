# The Weather Application
An android weather application written in Java. The app takes in the name of a city/state as input from the user then fetches the live weather data
for that location from the [Open Weather API](https://openweathermap.org/) and displays it on the screen.

# Features supported

After the user enters the name of a valid city/state from anywhere around the world and then clicks the button, the following information will be displayed onto the screen:
1) The name of the city
2) The initials of the country the city is located in
3) The temperature in degrees farhenheight 
4) The description of the weather
5) An icon corresponding to that description
6) The humidity percentage
7) The pressure
8) The "feels like" temperature
9) The wind speed in the area

# Example Runs





https://user-images.githubusercontent.com/80434026/171082778-9da1da93-40fd-4c4d-8922-a8a11edc7f58.mp4









# How it Works

The data is pulled from a url like [this](https://api.openweathermap.org/data/2.5/weather?q=Boston&appid=e53301e27efa0b66d05045d91b2742d3&units=imperial) and read using a buffered reader into a string. The relevant data is parsed by locating the indices of key words like "temp", "humidity", etc and making sure to only take their values. When parsing out the values I stopped when I encountered quote, comma or bracket characters. The valid characters were initially stored into a character array but then I realized the number of characters for each piece of data can differ so I opted to use a character ArrayList since their sizes aren’t fixed and can grow as needed. The characters were then used to build and return a string containing the relevant data. 

To display the icon, the icon code (e.g. "01d") was parsed and put into a url like: http://openweathermap.org/img/wn/01d@2x.png
Then the picasso library was used to extract the icon from this url and display it into an imageView object. 


# Additional Features

Some additional features I wanted to implement was displaying all of the information available from the open weather api, some of which include the timezone of the location, the sunrise/sunset time, the min/max temperature, etc. The main additional feature I wanted to implement was a dynamic background that would change depending on the description of the weather. I also wanted this background to be stylized to a 16-bit retro theme just so it could be a bit more unique than other weather applications. However, due to time constraints and other responsibilities I haven’t been able to accomplish that. I did see that android studio doesn’t support gif animations as backgrounds, but there are several viable workarounds which is something I am looking to explore in the feature.
