# DM_MemoryGame

In this project, we built a desktop application - a version of a memory game that combines matching by musical relationships.<br />

### Code structure
The application is written in the [MVC (Model, View, Controller) architecture](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) and contains SQL queries,
communication with a SQL Server & retrieving data from a database located on MySQL.<br />
* **Models package** - contains everything needed to deal with data: communicating with our DB in MySQL and retrieving the appropriate data from there using SQL queries,
managing the high scores tables, etc.<br />
* **Views package** - responsible for the visibility of the game.<br />
* **Controllers package** - contains controllers that are responsible for the execution and operation of the game.<br />

### Dataset
We used the [Million Song Dataset](http://millionsongdataset.com/). When we downloaded the data, it came in H5 files, and hence we used a Python script which turned this data into a [CSV file](https://github.com/adiyanai/DM_MemoryGame/blob/master/SongCSV.csv). The CSV file we extracted contains one large table of 10,000 songs so that each record contains information about one song.<br />
From this table we took the data needed to create the following tables: <br />
[artists](https://github.com/adiyanai/DM_MemoryGame/blob/master/artists.csv), [songs](https://github.com/adiyanai/DM_MemoryGame/blob/master/songs.csv),
[albums](https://github.com/adiyanai/DM_MemoryGame/blob/master/albums.csv), and the linking table [albums_artists](https://github.com/adiyanai/DM_MemoryGame/blob/master/albums_artists.csv) that links the Albums table to the Artists table.<br />

### About the game
The target audience of the application is game & music lovers.<br />
This is not a regular memory game where the matching is done according to identical cards, but a memory game where the matching is done by the relationship that the user
selects at the beginning of the game.<br />
The relationships that are offered are:
* Artist Name – Song Title
* Song Title – Album Name
* Artist Name – Album Name <br />

The user is asked for his name, then he chooses a musical relationship according to which he wants to match couples of playing cards,
and according to the relationship he chooses, the game board will be built.<br />
After selecting the desired relationship, another criterion that the user should choose is the level of difficulty - easy, medium, or hard.<br />
The difference between the difficulty levels will be expressed in two criteria:
* The number of cards that will appear on the board will be 4, 5, or 6 (depending on the difficulty level).
* The selected cards that will appear on the board (songs/singers/albums, according to the user's choice),
will be selected in a way that will increase the difficulty level of the game, the more difficult the user chooses.<br />
This functionality will be performed behind the scenes by SQL queries and data retrieving from the QSL server, and in practice - the higher the level of difficulty,
the less familiar the singer/ the owner of the album who appears (has fewer songs or older songs).<br />

After selecting the difficulty level, the user will be able to start the game and to win he must complete five levels from the selected difficulty.<br />
For a better user experience, the application will maintain a high scores table for each difficulty level separately, at which will appear the five best players.<br />
From the moment the cards appear on the board, a time counter will appear and will count the time it took the player to play.<br />
At the end of all the levels, if the player enters the five players who finished in the shortest time, he will be entered into the list of the high scores.<br />

### Game screens

![main](https://user-images.githubusercontent.com/45918740/98940538-c96dc800-24f3-11eb-98c0-d14bbecd6078.JPG)<br /><br /><br />

![name](https://user-images.githubusercontent.com/45918740/98940798-32edd680-24f4-11eb-9051-2cf14c0a4dce.JPG)<br /><br /><br />

![mode](https://user-images.githubusercontent.com/45918740/98940873-56b11c80-24f4-11eb-8cb7-9cf94181a70c.JPG)<br /><br /><br />

![difficulty](https://user-images.githubusercontent.com/45918740/98940924-71839100-24f4-11eb-997c-7a5cc50e01c8.JPG)<br /><br /><br />

![game1](https://user-images.githubusercontent.com/45918740/98940955-8102da00-24f4-11eb-8f0a-7d1ba14aad3f.JPG)<br /><br /><br />

![game2](https://user-images.githubusercontent.com/45918740/98941015-9aa42180-24f4-11eb-82ea-76232c625638.JPG)<br /><br /><br />

In case of selecting High Scores from the main menu: <br />
![hige scores type](https://user-images.githubusercontent.com/45918740/98940991-911ab980-24f4-11eb-981c-a8ad0126f490.JPG)<br /><br /><br />

![hige scores](https://user-images.githubusercontent.com/45918740/98941136-ca532980-24f4-11eb-9b75-9ed03dd93e1d.JPG)




