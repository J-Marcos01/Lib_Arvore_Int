package br.edu.fateczl.arvore_int;

public interface IArvore {

	public void Insert(int valor);
	public boolean exists(int valor);
	public void search(int valor);
	public void prefixSearch()throws Exception;
	public void postfixSearch()throws Exception;
	public void infixSearch()throws Exception;
	public void remove(int valor);
}
