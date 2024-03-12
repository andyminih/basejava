/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int count = 0; //количество объектов в массиве,
    Resume[] storage = new Resume[10000];

    void clear() {
        for (Resume r : storage) {
            r = null;
        }
        setCount(0);
    }

    void save(Resume r) {
        int i = size();

        if (i < 10000) {
            storage[i] = r;
            setCount(++i);
        }
    }

    Resume get(String uuid) {
        int i;

        for (i = 0; (i < size() && !storage[i].uuid.equals(uuid)); i++) {
        }
        return (i < size()) ? storage[i] : null;
    }

    void delete(String uuid) {
        int i;

        for (i = 0; (i < size() && !storage[i].uuid.equals(uuid)); i++) {
        }
        if (i < size()) {
            System.arraycopy(storage, i+1, storage, i, size()-i-1);
            //while (i < (size() - 1)) {
            //    storage[i] = storage[i + 1];
            //    i++;
            //}
            setCount(size() - 1);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];

        System.arraycopy(storage, 0, resumes, 0, size());
        return resumes;
    }

    int size() {
        // в текущей реализации используется переменная count, учитывая требование, что в элементах от 0 до size-1 отсутствуют null)
        // другой вариант реализации - подсчитвыать количестов объектов (не null) в массиве каждый раз, не используя count
        return getCount();
    }

    private int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }
}
