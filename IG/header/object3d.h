#ifndef OBJECT3D_H
#define OBJECT3D_H
#include <GL/gl.h>
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include "vector3d.h"
#include "camera.h"

class Object3D{
  protected:
    std::string name;
    std::vector<float> normals;// normals
    std::vector<float> vertices;// Vertices
    std::vector<float> normal_vertices;// normals
    std::vector<unsigned int> sides; // Sides
    std::vector<float> texture;
    bool has_texture = false;

    GLfloat color[4] = {0.5f, 0.3f, 0.4f, 1.0f};
    int covers;

    // Properties
    bool back;
    bool front;
    GLuint texture_id;
    GLenum material_type;
    GLenum draw_type;
  public:
    void setColor(GLfloat r, GLfloat g, GLfloat b){ color[0]=r; color[1]=g; color[2]=b; };
    void setMaterial(GLenum m){ material_type = m; };
    void setVisibility(float a, float b){ back = a; front = b; };
    virtual bool sameIntColor(unsigned char c[4]){
      return ((c[0] == (int)(color[0] * 255)) && (c[1] == (int)(color[1] * 255)) && (c[2] == (int)(color[2] * 255)));
    };
    // PLY
    void load_ply(std::string);

    // Draw
    virtual void draw(long int, bool);
    void draw_normals();

    // Drawing type
    virtual void setDrawType(GLenum);
    void setTexture(GLuint);

    // console
    void console_vertices();
    void console_faces();

    // getter
    void setName(std::string);
    void setVertices(float*);
    std::string getName();

    void normal_calculation();
    void new_object();
    // Empty
    Object3D();
    // Load From Arrays
    Object3D(float*, int*, int, int);
    // Load From PLY
    Object3D(std::string);
    ~Object3D();
};


/* Get and Set Methods (No explanation needed). */
void Object3D::setTexture(GLuint u){
  has_texture = true;
  texture_id = u;
};

void Object3D::setDrawType(GLenum t){ draw_type = t; };

void Object3D::setName(std::string _n){ name = _n; };
std::string Object3D::getName(){ return name; };


/* Draw method, need 1 parameter, the camera */
void Object3D::draw(long int delta, bool ex){
  if(sides.size() > 0){
    // Enabling
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
    //glEnable(GL_COLOR_MATERIAL); If not light
    // CULLING
    if(front ^ back){
      glEnable(GL_CULL_FACE);
      glCullFace(front ? GL_BACK : GL_FRONT);
    }
    // Buffers
    glNormalPointer(GL_FLOAT, 0, &normals[0]);
    glVertexPointer(3, GL_FLOAT, 0, &vertices[0]);
    if(ex){
      glColor3ub((int)(255 * color[0]),
                 (int)(255 * color[1]),
                 (int)(255 * color[2]));
    }else{
      if(has_texture){
		    glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glBindTexture(GL_TEXTURE_2D, texture_id);
        glTexCoordPointer(2, GL_FLOAT, 0, &texture[0]);
      	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glMatrixMode(GL_TEXTURE);

        glLoadIdentity();
        glTranslatef(0.5,0.5,0.0);
        glRotatef(delta,0.0,0.0,1.0);
        glTranslatef(-0.5,-0.5,0.0);
        glMatrixMode(GL_MODELVIEW);
      }else{ // Then it has a color
        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, color);
        //glColorMaterial( GL_FRONT_AND_BACK, GL_EMISSION);
      }
    }
    glPushMatrix();
      glPolygonMode(GL_FRONT_AND_BACK, draw_type);
      glDrawElements(GL_TRIANGLES, sides.size() - covers, GL_UNSIGNED_INT, &sides[0] + covers);
      //draw_normals();
    glPopMatrix();

    // Disabling
    glDisable(GL_CULL_FACE);
    //glDisable(GL_COLOR_MATERIAL);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_NORMAL_ARRAY);
  }
};

void Object3D::draw_normals(){
  for(int i = 0; i < sides.size(); i += 3){
    float cx = vertices[i + 0];
    float cy = vertices[i + 1];
    float cz = vertices[i + 2];

    glBegin(GL_LINES);
    glVertex3f(cx, cy, cz);
    glVertex3f(cx + normal_vertices[i + 0], cy + normal_vertices[i + 1], cz + normal_vertices[i + 2]);
    glEnd();
  }
};

/* Normal calculation method (No parameters) */
void Object3D::normal_calculation(){
  int total = sides.size();

  normal_vertices.resize(total);
  std::fill(normal_vertices.begin(), normal_vertices.end(), 0);
  int mds = total / 3;
  int times[mds] = {0};
  for(int i = 0; i < total; i += 3){
    int f0 = sides[i + 0];
    int f1 = sides[i + 1];
    int f2 = sides[i + 2];
    ++times[f0]; ++times[f1]; ++times[f2];
    f0 *= 3; f1 *= 3; f2 *= 3;
    float cx = vertices[f0 + 0];
    float x1 = cx - vertices[f1 + 0];
    float x2 = cx - vertices[f2 + 0];

    float cy = vertices[f0 + 1];
    float y1 = cy - vertices[f1 + 1];
    float y2 = cy - vertices[f2 + 1];

    float cz = vertices[f0 + 2];
    float z1 = cz - vertices[f1 + 2];
    float z2 = cz - vertices[f2 + 2];

    float mx = y1 * z2 - z1 * y2;
    float my = z1 * x2 - x1 * z2;
    float mz = x1 * y2 - y1 * x2;
    float md = sqrt(mx * mx + my * my + mz * mz);

    float nx = mx / md;
    float ny = my / md;
    float nz = mz / md;

    normals.insert(normals.end(), {nx, ny, nz});
    normal_vertices[f0 + 0] += nx; normal_vertices[f0 + 1] += ny; normal_vertices[f0 + 2] += nz;
    normal_vertices[f1 + 1] += nx; normal_vertices[f1 + 1] += ny; normal_vertices[f1 + 2] += nz;
    normal_vertices[f2 + 2] += nx; normal_vertices[f2 + 1] += ny; normal_vertices[f2 + 2] += nz;
  }

  for(int i = 0; i < mds; ++i){
    int total = times[i];
    normal_vertices[i * 3 + 0] /= total; normal_vertices[i * 3 + 1] /= total; normal_vertices[i * 3 + 2] /= total;
  }
};

void Object3D::console_vertices(){
  std::cout << "Vertices from the object" << std::endl;
  for(int i = 0; i < vertices.size() / 3; i++){
    for(int j = 0; j < 3; j++){
      std::cout << vertices[i * 3 + j] << ',';
    }
    std::cout << std::endl;
  }
};

void Object3D::console_faces(){
  std::cout << "Faces from the object" << std::endl;
  for(int i = 0; i < sides.size() / 3; i++){
    for(int j = 0; j < 3; j++){
      std::cout << sides[i * 3 + j] << ',';
    }
    std::cout << std::endl;
  }
};

/* New object method */
void Object3D::new_object(){
  covers = 0;
  back = false;
  front = true;
  draw_type = GL_FILL;
  material_type = GL_AMBIENT_AND_DIFFUSE;
  has_texture = false;
};

/* Empty contructor */
Object3D::Object3D(){
  new_object();
};

/* Constructor by vectors */
Object3D::Object3D(float* _v, int* _s, int total_vertices, int total_sides){
  new_object();
  vertices.assign(_v, _v + total_vertices);
  sides.assign(_s, _s + total_sides);
  normal_calculation();
};

/* Constructor by path, it has to be existent. */
Object3D::Object3D(std::string name){
  new_object();
  load_ply(name);
  normal_calculation();
};

void Object3D::load_ply(std::string name){
  std::ifstream ply_object(name.c_str());
  if (ply_object.is_open()){
    std::string str;
    ply_object >> str;
    if(str == "ply"){
      std::cout << "Loading " << name << " PLY File...\n";
      ply_object >> str;
      while(str != "end_header"){
        if(str == "element"){
          ply_object >> str;
          int size;
          ply_object >> size;
          std::cout << str << " -> " << size << "\n";
          if(str == "vertex"){ vertices.resize(3 * size); }
          else if(str == "face"){ sides.resize(3 * size); }
        }
        ply_object >> str;
      }

      // Load Vertex
      for(int i = 0; i < vertices.size(); i++){
        float v;
        ply_object >> v;
        vertices[i] = v;
      }
      // Load Edges
      for(int i = 0; i < sides.size(); i++){
        int e;
        if(i % 3 == 0){ ply_object >> e; }
        ply_object >> e;
        sides[i] = e;
      }
    }
  }
  ply_object.close();
};

/* Destructor. */
Object3D::~Object3D(){
  normals.clear();// normals
  vertices.clear();// Vertices
  sides.clear(); // Sides
};
#endif
