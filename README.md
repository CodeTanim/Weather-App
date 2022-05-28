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


<p align="center"> <img src="http://g.recordit.co/hCpMGBG2l4.gif" width="200" > </p>



# How it Works

The data is pulled from a url like [this](https://api.openweathermap.org/data/2.5/weather?q=Boston&appid=e53301e27efa0b66d05045d91b2742d3&units=imperial) and read using a buffered reader into a string. The relevant data is parsed by locating the indices of key words like "temp", "humidity", etc and making sure to only take their values. When parsing out the values I stopped when I encountered quote, comma or bracket characters. The valid characters were initially stored into a character array but then I realized the number of characters for each piece of data can differ so I opted to use a character ArrayList since their sizes aren’t fixed and can grow as needed. The characters were then used to build and return a string containing the relevant data. 

To display the icon, the icon code (e.g. "01d") was parsed and put into a url like: http://openweathermap.org/img/wn/01d@2x.png
Then the picasso library was used to extract the icon from this url and display it into an imageView object. 


