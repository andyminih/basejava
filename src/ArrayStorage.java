/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    //количество резюме в массиве
    private int count = 0;
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < count; i++) {
            storage[i] = null;
        }
        count = 0;
    }

    void save(Resume r) {

        if (count < 10000) {
            storage[count] = r;
            count++;
        }
    }

    Resume get(String uuid) {
        int i = 0;

        while (i < count && !storage[i].uuid.equals(uuid)) {
            i++;
        }

        return (i < count) ? storage[i] : null;
    }

    void delete(String uuid) {
        int i = 0;

        while (i < count && !storage[i].uuid.equals(uuid)) {
            i++;
        }

        if (i < count) {
            System.arraycopy(storage, i + 1, storage, i, count - i - 1);
            count--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[count];

        System.arraycopy(storage, 0, resumes, 0, count);
        return resumes;
    }

    int size() {
        return count;
    }

}
