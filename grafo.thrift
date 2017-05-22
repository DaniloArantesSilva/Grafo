namespace java grafo

typedef i32 int

exception VerticeNaoEncontrado
{
1:string erro
}

exception ArestaNaoEncontrada
{
1:string erro
}

struct Vertice {
  1: int nome;
  2: int cor;
  3: string descricao;
  4: double peso; 
}

struct Aresta {
  1: int vnome1;
  2: int vnome2;
  3: double peso;
  4: bool flg_bidirecional;
  5: string descricao;
}

service Grafo
{
  bool insereVertice(1:int nome, 2:int cor, 3:string descricao, 4:double peso),
  bool insereAresta(1:int vnome1, 2:int vnome2, 3:double peso, 4:bool flg_bidirecional, 5:string descricao) throws (1:VerticeNaoEncontrado vne),
  bool verificaExistenciaVertice(1:int nome),
  bool verificaExistenciaAresta(1:int vnome1, 2:int vnome2),
  string buscaVertice(1:int nome) throws (1:VerticeNaoEncontrado vne),
  string buscaAresta(1:int vnome1, 2:int vnome2) throws (1:ArestaNaoEncontrada ane),
  bool alteraVertice(1:int nome, 2:int cor, 3:string descricao, 4:double peso) throws (1:VerticeNaoEncontrado vne),
  bool alteraAresta(1:int vnome1, 2:int vnome2, 3:double peso, 4:bool flg_bidirecional, 5:string descricao) throws (1:ArestaNaoEncontrada ane),
  bool removeVertice(1:int nome) throws (1:VerticeNaoEncontrado vne),
  bool removeAresta(1:int vnome1, 2:int vnome2) throws (1:ArestaNaoEncontrada ane),
  string listaVerticeAresta(1:int vnome1, 2:int vnome2) throws (1:VerticeNaoEncontrado vne, 2:ArestaNaoEncontrada ane),
  string listaArestaVertice(1:int nome),
  string listaVerticeVizinho(1:int nome)
}
