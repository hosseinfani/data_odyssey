#include <stdio.h>
#include <string.h>

#include "../ca.uwindsor.cs.comp3150.cmn/Director.c"
#include "../ca.uwindsor.cs.comp3150.cmn/Movie.c"

#define DBSIZE 100

void addMovie(int movieId, struct Movie movies[]) {
	
	struct Movie newMovie;	
	newMovie.id = movieId;
	printf("Enter movie title:\n");	
	scanf("%s", newMovie.title);

	struct Director newDirector;
	printf("Enter director name:\n");
	scanf("%s", newDirector.name);
	
	//Relationship between movie and director
	strcpy(newMovie.director.name, newDirector.name);
	
	//Add to our in-memory movie database
	movies[movieId] = newMovie;
	printf("Movie '%s' has been added with Id='%d'\n", newMovie.title, newMovie.id);	
}

void printMovieByName(struct Movie movies[]) {
	//find the movie by its name and print out its full info.
}

int printMovieById(struct Movie movies[]) {

	printf("Enter movie id:\n");
	int id;
	scanf("%d", &id);
	for(int i=0; i < DBSIZE; ++i){
		if(movies[i].id == id){
			printf("Title: %s\nDirector: %s\n", movies[i].title, movies[i].director.name);
			return id;
		}
	}
	printf("Movie not found!\n");
	return -1;
}

void printAllMoviesByDirector(struct Movie movies[]) {
	printf("Enter director name: \n");
	char directorName[100];
	scanf("%s", directorName);
	for(int i=0; i < DBSIZE; ++i){
		if(movies[i].id > 0 && strcmp(movies[i].director.name, directorName) == 0){
			printf("Movie title: %s\n", movies[i].title);
		}                
	}
}

void printAllMovies(struct Movie movies[]) {
	//loop over all movies and print out the info.
}

void editMovieById(struct Movie movies[]) {
	
	int id = printMovieById(movies);
	if(id < 0) return;
	
	printf("Enter movie new title:\n");	
	scanf("%s", movies[id].title);
	
	printf("Enter new director name: \n");	
	scanf("%s", movies[id].director.name);
	
	printf("Movie with Id='%d' has been updated", id);
}

void deleteMovieById(struct Movie movies[]) {
	//find the movie by Id and remove it from array
}

void main() {

	//Our in-memory movie database
	struct Movie movies[DBSIZE];
	
	//Auto increment id
	int movieId = 1;
	
	char* commandList = 
			"1) Add Movie\n\
2) Print Movie by Id\n\
3) *Print Movie by Title\n\
4) *Print All Movies\n\
5) Print All Movies of a Director\n\
6) Edit Movie by Id\n\
7) *Delete Movie by Id\n\
0) Quit\n\
Enter command: ";
	int running = 1;
	int commandId;
	while (running) {		
		printf(commandList);
		scanf("%d", &commandId);
		switch (commandId) {
			case -1:
				break;
			case 0:
				printf("Bye.\n");
				running = 0;
				break;
			case 1:
				addMovie(movieId, movies);
				movieId++;
				break;
			case 2:
				printMovieById(movies);
				break;
			case 3:
				printMovieByName(movies);
				break;
			case 4:
				printAllMovies(movies);
				break;
			case 5:
				printAllMoviesByDirector(movies);
				break;
			case 6:
				editMovieById(movies);
				break;
			case 7:
				deleteMovieById(movies);
				break;
			default:
				printf("Command not recognized!");
				break;		
		}
		printf("\n\n");
	
	}
}

