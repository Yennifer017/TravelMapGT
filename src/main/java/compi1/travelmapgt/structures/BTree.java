package compi1.travelmapgt.structures;

import compi1.travelmapgt.exceptions.NoDataFoundException;

/**
 *
 * @author yennifer
 * @param <T>
 */
public class BTree<T extends Comparable<T>> {

    private BTreePage<T> raiz;
    private int size;

    public BTree(int orden) {
        raiz = new BTreePage<>(orden);
        size = 0;
    }

    public void insert(T content) {
        raiz.insert(content);
        size++;
    }

    public T find(T comparable) throws NoDataFoundException {
        return raiz.get(comparable);
    }

    public boolean isEmpty() {
        return raiz.isEmpty();
    }

    public BTreePage<T> getRaiz() {
        return this.raiz;
    }

    public boolean exist(T comparable) {
        try {
            raiz.get(comparable);
            return true;
        } catch (NoDataFoundException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public int getProfundidad() {
        if (isEmpty()) {
            return 0;
        } else {
            int counter = 0;
            BTreePage page = raiz;
            while (true) {
                if(page.getPunteros().getFirst() == null){
                    break;
                }
                counter++;
            }
            return counter;
        }
    }
    
    public int getSize(){
        return size;
    }

}
