package compi1.travelmapgt.structures;

import compi1.travelmapgt.exceptions.NoDataFoundException;
import java.util.LinkedList;

/**
 *
 * @author yennifer
 * @param <T>
 */
public class BTreePage<T extends Comparable<T>> {

    private LinkedList<T> nodes;
    private LinkedList<BTreePage<T>> punteros;

    private int totalElements;
    private int order;

    private BTreePage<T> father;
    private int fatherRefNum;

    /**
     * Crea una pagina raiz
     *
     * @param order
     */
    public BTreePage(int order) {
        nodes = new LinkedList<>();
        punteros = new LinkedList<>();
        totalElements = 0;
        this.order = order;
        this.father = null;
    }

    /**
     * Crea una pagina con un padre asociado
     */
    private BTreePage(int order, BTreePage<T> father, int fatherRefNum) {
        nodes = new LinkedList<>();
        punteros = new LinkedList<>();
        totalElements = 0;
        this.order = order;
        this.father = father;
        this.fatherRefNum = fatherRefNum;
    }

    /**
     * Inserta un nodo en la pagina del arbol
     *
     * @param content
     */
    public void insert(T content) {
        if (punteros.getFirst() == null) {
            insertInLevel(content);
            this.growUp();
        } else {
            //irse para las ramas
            boolean inserted = false;
            int index;
            for (index = 0; index < nodes.size(); index++) {
                if (content.compareTo(nodes.get(index)) < 0) {
                    punteros.get(index).insert(content);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                punteros.get(index).insert(content);
            }
        }
    }

    /**
     * Inserta el nodo en el nivel del arbol
     */
    private void insertInLevel(T content) {
        boolean inserted = false;
        int index;
        for (index = 0; index < nodes.size(); index++) {
            if (content.compareTo(nodes.get(index)) >= 0) {
                nodes.add(index, content);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            nodes.add(content);
        }
        totalElements++;
    }

    /**
     * Evalua si el arbol supera su orden y necesita dividirse
     */
    private boolean isFull() {
        return totalElements == order;
    }

    /**
     * agrega una lista de nodos estrictamente ordenados
     */
    private void internalInsert(LinkedList<T> orderedNodes) {
        nodes.addAll(orderedNodes);
        totalElements = nodes.size();
    }

    /**
     * Hace crecer la pagina
     */
    private void growUp() {
        if (isFull()) {
            //crea nodos mas profundos
            int middle = (int) order / 2;
            BTreePage<T> left = new BTreePage<>(order, this, 0);
            left.internalInsert((LinkedList<T>) nodes.subList(0, middle));
            BTreePage<T> right = new BTreePage<>(order, this, 1);
            right.internalInsert((LinkedList<T>) nodes.subList(middle + 1, nodes.size()));

            //mantiene la pagina actual
            T middleNode = nodes.get(middle);
            this.reset();
            nodes.add(middleNode);

            //crea referencias
            if (father != null) {
                father.concatReference(this);
            } else {
                punteros.addFirst(left); //pos 0
                punteros.addLast(right); //pos 1
            }
        }
    }

    /**
     * Agrega una referencia de un nivel mas bajo
     */
    private void concatReference(BTreePage<T> subPage) {
        this.nodes.add(subPage.fatherRefNum, subPage.nodes.get(0)); //agrega el medio en la posicion adecuada
        this.totalElements++;
        //arreglar referncias de los hijos de subpage
        subPage.punteros.getFirst().fatherRefNum = subPage.fatherRefNum;
        subPage.punteros.getLast().fatherRefNum = subPage.fatherRefNum + 1;
        subPage.punteros.getLast().father = this;
        subPage.punteros.getFirst().father = this;
        //agregar al padre (this)
        this.punteros.add(subPage.fatherRefNum, subPage.punteros.getFirst());
        this.punteros.add(subPage.fatherRefNum + 1, subPage.punteros.getLast());
    }

    /**
     * Elimina todos los datos de la pagina
     */
    private void reset() {
        nodes.clear();
        totalElements = 0;
    }

    public boolean isEmpty() {
        return totalElements == 0;
    }

    /**
     * Busca en el nodo un objeto
     *
     * @param comparable
     * @return el objeto si se encuentra
     * @throws compi1.travelmapgt.exceptions.NoDataFoundException
     */
    public T get(T comparable) throws NoDataFoundException {
        if (isEmpty()) {
            throw new NoDataFoundException();
        } else {
            int i;
            for (i = 0; i < nodes.size(); i++) {
                T node = nodes.get(i);
                if (node.compareTo(comparable) == 0) {
                    return node;
                } else if (node.compareTo(comparable) < 0) {
                    if (punteros.get(i) != null) {
                        return punteros.get(i).get(comparable);
                    } else {
                        throw new NoDataFoundException();
                    }
                }
            }
            if (punteros.get(i) != null) {
                return punteros.get(i).get(comparable);
            } else {
                throw new NoDataFoundException();
            }
        }
    }
    
    public LinkedList<T> getNodes(){
        return this.nodes;
    }
    
    public LinkedList<BTreePage<T>> getPunteros(){
        return this.punteros;
    }

}
