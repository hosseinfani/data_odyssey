#include <stdio.h>
#include <sqlite3.h> 

static int callback(void* data, int argc, char** argv, char** ColName){
   
   for(int i = 0; i<argc; i++){
      printf("%s = %s\n", ColName[i], argv[i]);
   }
   
   return 0;
}


int main(int argc, char* argv[]) {
   sqlite3* db;
   int c;

   c = sqlite3_open_v2("Moviesion.db", &db, SQLITE_OPEN_READWRITE, NULL);

   if(c) {
      printf("Can't open database! %s\n", sqlite3_errmsg(db));
      return(0);
   } else {
      printf("Connect to database successfully.\n");
   }
   
   char* errMsg = 0; 
   //create SQL statement
   char* sql = "CREATE TABLE Movie("  \
         "ID INTEGER PRIMARY KEY     NOT NULL," \
         "TITLE          VARCHAR(255)    NOT NULL);";

   //execute SQL statement
   int r = sqlite3_exec(db, sql, NULL, 0, &errMsg);
      
   if( r != SQLITE_OK ){
       printf("SQL error: %s\n", errMsg);
       sqlite3_free(errMsg);
   } 
   else {
       printf("Table created successfully\n");
   }
   
   sql = "INSERT INTO Movie(Id, Title) VALUES(330, 'Psycho');";

   //execute SQL statement
   r = sqlite3_exec(db, sql, NULL, 0, &errMsg);
      
   if( r != SQLITE_OK ){
       printf("SQL error: %s\n", errMsg);
       sqlite3_free(errMsg);
   } 
   else {
       printf("Movie inserted successfully\n");
   }

   sql = "SELECT * from Movie";

   //execute SQL statement
   r = sqlite3_exec(db, sql, callback, 0, &errMsg);
   
   if( r != SQLITE_OK ){
       printf("SQL error: %s\n", errMsg);
       sqlite3_free(errMsg);
   } 
   else {
       printf("Movies are retrieved successfully\n");
   }

   sqlite3_close(db);
}