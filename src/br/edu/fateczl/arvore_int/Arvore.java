package br.edu.fateczl.arvore_int;

import java.util.NoSuchElementException;

public class Arvore implements IArvore {

	No raiz;

	public Arvore() {
		raiz = null;
	}

	@Override
	public void Insert(int valor) {
		No elemento = new No();
		elemento.dado = valor;
		elemento.direita = null;
		elemento.esquerda = null;
		insertLeaf(elemento, raiz);

	}

	private void insertLeaf(No elemento, No raizSubArvore) {
		if (raiz == null) {
			raiz = elemento;
		} else {
			if (elemento.dado < raizSubArvore.dado) {
				if (raizSubArvore.esquerda == null) {
					raizSubArvore.esquerda = elemento;
				} else {
					insertLeaf(elemento, raizSubArvore.esquerda);
				}
			}
			if (elemento.dado > raizSubArvore.dado) {
				if (raizSubArvore.direita == null) {
					raizSubArvore.direita = elemento;
				} else {
					insertLeaf(elemento, raizSubArvore.direita);
				}
			}
		}

	}

	@Override
	public boolean exists(int valor) {
		try {
			nodeSearch(raiz, valor);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void search(int valor) {
		try {
			No elemento = nodeSearch(raiz, valor);
			System.out.println(elemento.dado);
			int level = nodeLevel(raiz, valor);
			System.out.println("Level :" + level);
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
		} catch (NullPointerException e) {
			System.err.println("Valor inexistente");
		}
	}

	private int nodeLevel(No raizSubArvore, int valor) throws NoSuchElementException {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.direita, valor);
		}
		return 0;

	}

	private No nodeSearch(No raizSubArvore, int valor) {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.direita, valor);
		} else {
			return raizSubArvore;
		}
	}

	@Override
	public void prefixSearch() throws Exception {
		prefix(raiz);
	}

	private void prefix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else {
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.esquerda != null) {
				prefix(raizSubArvore.esquerda);
			}
			if (raizSubArvore.direita != null) {
				prefix(raizSubArvore.direita);
			}
		}
	}

	@Override
	public void postfixSearch() throws Exception {
		postfix(raiz);
	}

	private void postfix(No raizSubArvore) {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore Vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				postfix(raizSubArvore.esquerda);
			}
		}
		if (raizSubArvore.direita != null) {
			postfix(raizSubArvore.direita);
		}
		System.out.print(raizSubArvore.dado + " ");
	}

	@Override
	public void infixSearch() throws Exception {
		infix(raiz);
	}

	private void infix(No raizSubArvore) {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore Vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				infix(raizSubArvore.esquerda);
			}
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.direita != null) {
				infix(raizSubArvore.direita);
			}
		}
	}

	@Override
	public void remove(int valor) {
		try {
			removeChild(raiz, valor);
		} catch (Exception e) {
			System.err.println("Valor Inexistente");
		}
	}

	private void removeChild(No raizSubArvore, int valor) {
		No no = nodeSearch(raizSubArvore, valor);
		if (no != null) {
			No pai = nodeParent(null, raiz, no.dado);
			if (no.esquerda != null && no.direita != null) {
				No noTroca = no.esquerda;
				while (noTroca.direita != null) {
					noTroca = noTroca.direita;
				}
				pai = nodeParent(null, raiz, noTroca.dado);
				no.dado = noTroca.dado;
				noTroca.dado = valor;
				removeOneOrZeroLeaf(pai, noTroca);
			} else {
				removeOneOrZeroLeaf(pai, no);
			}
		} else {
			throw new NoSuchElementException("Valor inexistente");
		}

	}

	private void removeOneOrZeroLeaf(No parent, No no) {
		if (no.esquerda == null && no.direita == null) {
			if (parent == null) {
				raiz = null;
			} else {
				change(parent, no, null);
			}
		} else if (no.direita != null) {
			if (parent == null) {
				raiz = no.direita;
			} else {
				change(parent, no, no.direita);
			}
		} else if (no.direita == null) {
			if (parent == null) {
				raiz = no.esquerda;
			} else {
				change(parent, no, no.esquerda);
			}
		}
	}

	private void change(No parent, No no, No novoNo) {
		if (parent.esquerda != null && parent.esquerda.dado == no.dado) {
			parent.esquerda = novoNo;
		} else if (parent.direita.dado == no.dado) {
			parent.direita = novoNo;
		}

	}

	private No nodeParent(No parent, No raizSubArvore, int valor) {
		if (raiz == null) {
			throw new NoSuchElementException("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.direita, valor);
		} else {
			if (parent == null) {
				return raiz;
			} else {
				return parent;
			}
		}
	}
}
