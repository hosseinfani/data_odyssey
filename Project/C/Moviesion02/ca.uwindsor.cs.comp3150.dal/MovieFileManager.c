#include <stdlib.h>

/**
 *
 * @author hosseinfani
 */

#define FILE_NAME "../ca.uwindsor.cs.comp3150.dal/Moviesion.txt"
#define RECORD_SEP '@'
#define VALUE_SEP '*'

int getLastId() {
	//Not a good way to genrate Ids. Why?
}

int getMaxId(){
	FILE* db =fopen(FILE_NAME, "r");
	if(db == NULL) return 0;
	//printf("the file opened.%d", db);
	char idstr[100];
	struct Movie cursor;

	int maxId = 0;
	int movieCounter = 0;
	int valueCounter = 0;	
	int charCounter = 0;
	
	while(1) {
		char c = fgetc(db);
		if(feof(db)) break;
		
		//similar to getMovieById
		if(c == RECORD_SEP){
			movieCounter = movieCounter + 1;
			valueCounter = 0;
			charCounter = 0;
			if(cursor.id > maxId){
				maxId = cursor.id;
			}
			continue;
		}
		if(c == VALUE_SEP){
			valueCounter = valueCounter + 1;
			charCounter = 0;
			continue;
		}
		switch(valueCounter){
			case 0://id
				idstr[charCounter] = c;
				charCounter = charCounter + 1;
				break;
			case 1:
				cursor.id = atoi(idstr);
				cursor.title[charCounter] = c;
				charCounter = charCounter + 1;
				break;
			case 2:
				cursor.director.name[charCounter] = c;
				charCounter = charCounter +  1;
				break;			
		}
		
		//This program knows that the first piece of 
		//information is the movie Id. How about other programs? 
		
	}
	fclose(db);
	//printf("the file closed.");
	//printf("%d\n", maxId);
	return maxId;
}

int insertMovie(struct Movie newMovie){
	int newId;
	
	//newId = getLastId() + 1;
	//LastId does not work!(why?)
	//delete last movie, add new movie!
	//edit a movie (delete the movie, add updated movie)
	
	newId = getMaxId() + 1;
	int dummy;
	scanf("%d", &dummy);
	
	FILE* db = fopen(FILE_NAME, "a+");
	//Append new movie to the end of file
	fseek(db, 0, SEEK_END);
	//Tab separated values => \t
	//Line separated movies => \n
	//Why not comma separated values?
	
	//Movie's information
	fprintf(db, "%d%c%s%c", newId, VALUE_SEP, newMovie.title, VALUE_SEP);
	
	//Movie's director information
	//Atomicity test: format exception! but we have partial writing in file!!
    //fprintf(db, "%s\t%s\r\n", newMovie.director.name);
            
	//Bug fixed
	fprintf(db, "%s%c", newMovie.director.name, RECORD_SEP);
	fclose(db);
	return newId;
}

struct Movie getMovieById(int id){
	FILE* db =fopen(FILE_NAME, "r");
	//printf("the file opened.%d", db);
	char idstr[100];
	struct Movie cursor;
	
	int movieCounter = 0;
	int valueCounter = 0;	
	int charCounter = 0;
		
	while(1) {
		char c = fgetc(db);
		if(feof(db)) break;
		//printf("before: %c\n", c);
		//similar to getMovieById
		if(c == RECORD_SEP){
			movieCounter = movieCounter + 1;
			valueCounter = 0;
			charCounter = 0;
			if(cursor.id == id){
				fclose(db);				
				return cursor;
			}
			continue;
		}
		if(c == VALUE_SEP){
			valueCounter = valueCounter + 1;
			charCounter = 0;
			continue;
		}
		if(valueCounter == 0){//id
				idstr[charCounter] = c;
				charCounter = charCounter + 1;
		}
		else if(valueCounter == 1){
				cursor.id = atoi(idstr);
				cursor.title[charCounter] = c;
				charCounter = charCounter + 1;
		}
		else if(valueCounter == 2){
				cursor.director.name[charCounter] = c;
				charCounter = charCounter +  1;
		}
		//printf("after: %c\n", c);
		//int a;
		//scanf("%d", &a);		
	}
	fclose(db);
	struct Movie dummy;
	return dummy;
}

struct  Movie* getMoviesByDirector(char directorName[]){
	FILE* db =fopen(FILE_NAME, "a+");
	struct Movie results[100];
	struct Movie cursor;//temp holder of current record
	char idstr[100];
	
	int i = 0;
	int movieCounter = 0;
	int valueCounter = 0;	
	int charCounter = 0;
	while(1) {
		char c = fgetc(db);
		if(feof(db)) break;
		
		//similar to getMovieById
		if(c == RECORD_SEP){
			movieCounter = movieCounter + 1;
			valueCounter = 0;
			charCounter = 0;
			if(strcmp(cursor.director.name, directorName) == 0){
				results[i] = cursor;
				i = i + 1;
			}
			continue;
		}
		if(c == VALUE_SEP){
			valueCounter = valueCounter + 1;
			charCounter = 0;
			continue;
		}
		switch(valueCounter){
			case 0://id
				idstr[charCounter] = c;
				charCounter = charCounter + 1;
				break;
			case 1:
				cursor.id = atoi(idstr);
				cursor.title[charCounter] = c;
				charCounter = charCounter + 1;
				break;
			case 2:
				cursor.director.name[charCounter] = c;
				charCounter = charCounter +  1;
				break;			
		}		
		
	}
	fclose(db);
	return NULL;
}

void deleteById(int id){
	//physical deletion: shift the following chars to the left (overwrite)
	//logical deletion: make the id = -1, then garbage collection!
}


