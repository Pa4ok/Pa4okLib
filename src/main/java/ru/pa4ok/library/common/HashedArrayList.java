package ru.pa4ok.library.common;

import java.util.*;

public class HashedArrayList<T> extends ArrayList<T> {
    private Set<T> hashed;

    public HashedArrayList() {
        this.hashed = new HashSet<T>();
    }

    public HashedArrayList(int initialCapacity) {
        super(initialCapacity);
        this.hashed = new HashSet<T>(initialCapacity);
    }

    @Override
    public boolean add(T t) {
        synchronized (this) {
            boolean flag = this.hashed.add(t);
            if (flag)
                super.add(t);
            return flag;
        }
    }

    @Override
    public void add(int pos, T t) {
        synchronized (this) {
            boolean flag = this.hashed.add(t);
            if (flag)
                super.add(pos, t);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> elements) {
        synchronized (this) {
            boolean flag = this.hashed.addAll(elements);
            if (flag)
                super.addAll(elements);
            return flag;
        }
    }

    @Override
    public boolean addAll(int pos, Collection<? extends T> elements) {
        synchronized (this) {
            boolean flag = this.hashed.addAll(elements);
            if (flag)
                super.addAll(pos, elements);
            return flag;
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            this.hashed.clear();
            super.clear();
        }
    }

    @Override
    public boolean contains(Object element) {
        synchronized (this) {
            return this.hashed.contains(element);
        }
    }

    @Override
    public boolean containsAll(Collection<?> elements) {
        synchronized (this) {
            return this.hashed.containsAll(elements);
        }
    }

    @Override
    public T get(int pos) {
        synchronized (this) {
            return super.get(pos);
        }
    }

    @Override
    public int indexOf(Object element) {
        synchronized (this) {
            return super.indexOf(element);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this) {
            return super.isEmpty();
        }
    }

    @Override
    public int lastIndexOf(Object element) {
        synchronized (this) {
            return this.hashed.contains(element) ? super.lastIndexOf(element) : -1;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashedArrayIterator(super.iterator());
    }

    @Override
    public ListIterator<T> listIterator() {
        return new HashedArrayListIterator(super.listIterator());
    }

    @Override
    public ListIterator<T> listIterator(int arg0) {
        ListIterator<T> listIterator;
        synchronized (this) {
            listIterator = super.listIterator(arg0);
        }
        return new HashedArrayListIterator(listIterator);
    }

    @Override
    public boolean remove(Object element) {
        synchronized (this) {
            boolean flag = this.hashed.remove(element);
            if (flag)
                super.remove(element);
            return flag;
        }
    }

    @Override
    public T remove(int pos) {
        synchronized (this) {
            T te = super.remove(pos);
            if (te != null)
                this.hashed.remove(te);
            return te;
        }
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        synchronized (this) {
            boolean flag = this.hashed.removeAll(elements);
            if (flag) {
                super.clear();
                super.addAll(this.hashed);
            }
            return flag;
        }
    }

    @Override
    public boolean retainAll(Collection<?> elements) {
        synchronized (this) {
            boolean flag = this.hashed.retainAll(elements);
            if (flag)
                super.retainAll(elements);
            return flag;
        }
    }

    @Override
    public T set(int pos, T t) {
        synchronized (this) {
            T te = super.set(pos, t);
            if (te != t) {
                // TODO Use Multiset to handle duplicates in list
                this.hashed.remove(te);
                this.hashed.add(t);
            }
            return te;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            return super.size();
        }
    }

    @Override
    public List<T> subList(int startPos, int endPos) {
        // TODO Synchronize it
        return super.subList(startPos, endPos);
    }

    @Override
    public Object[] toArray() {
        synchronized (this) {
            return super.toArray();
        }
    }

    @Override
    public <K> K[] toArray(K[] arr) {
        synchronized (this) {
            return super.toArray(arr);
        }
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        synchronized (this) {
            return super.equals(o);
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            return super.toString();
        }
    }

    @Override
    public void ensureCapacity(int minCapacity) {
        synchronized (this) {
            super.ensureCapacity(minCapacity);
        }
    }

    @Override
    public void trimToSize() {
        synchronized (this) {
            super.trimToSize();
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        synchronized (this) {
            super.sort(c);
        }
    }

    @Override
    public Object clone() {
        synchronized (this) {
            HashedArrayList<T> list = (HashedArrayList<T>) super.clone();
            list.hashed = new HashSet<T>(this.hashed);
            return list;
        }
    }

    private class HashedArrayIterator implements Iterator<T> {
        private final Iterator<T> iterator;
        private T last = null;

        public HashedArrayIterator(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            synchronized (HashedArrayList.this) {
                return this.iterator.hasNext();
            }
        }

        @Override
        public T next() {
            synchronized (HashedArrayList.this) {
                this.last = this.iterator.next();
            }
            return this.last;
        }

        @Override
        public void remove() {
            synchronized (HashedArrayList.this) {
                this.iterator.remove();
                HashedArrayList.this.hashed.remove(this.last);
            }
        }
    }

    private class HashedArrayListIterator implements ListIterator<T> {
        private final ListIterator<T> listIterator;
        private T lastRet = null;

        public HashedArrayListIterator(ListIterator<T> listIterator) {
            this.listIterator = listIterator;
        }

        @Override
        public void add(T arg0) {
            synchronized (HashedArrayList.this) {
                boolean flag = HashedArrayList.this.hashed.add(arg0);
                if (flag)
                    this.listIterator.add(arg0);
            }
        }

        @Override
        public boolean hasNext() {
            synchronized (HashedArrayList.this) {
                return this.listIterator.hasNext();
            }
        }

        @Override
        public boolean hasPrevious() {
            return this.listIterator.hasPrevious();
        }

        @Override
        public T next() {
            synchronized (HashedArrayList.this) {
                this.lastRet = this.listIterator.next();
            }
            return this.lastRet;
        }

        @Override
        public int nextIndex() {
            return this.listIterator.nextIndex();
        }

        @Override
        public T previous() {
            synchronized (HashedArrayList.this) {
                this.lastRet = this.listIterator.previous();
            }
            return this.lastRet;
        }

        @Override
        public int previousIndex() {
            return this.listIterator.previousIndex();
        }

        @Override
        public void remove() {
            synchronized (HashedArrayList.this) {
                this.listIterator.remove();
                HashedArrayList.this.hashed.remove(this.lastRet);
            }
        }

        @Override
        public void set(T t) {
            synchronized (HashedArrayList.this) {
                this.listIterator.set(t);
                HashedArrayList.this.hashed.remove(this.lastRet);
                HashedArrayList.this.hashed.add(t);
            }
        }
    }
}
