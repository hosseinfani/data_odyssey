struct Movie {

    int id;
    char title[100];
    struct Director director;
    //other attributes/fields/properties
};

/*
#include "Director.c"
int main() {
  struct Director d;
  d.id = 1;
  d.name = "Stanley Kubrick";
  
  struct Movie m;
  m.id = 1;
  m.title = "2001: A Space Odyssey";
  m.director = d;
  return 0;
}*/
