<div align="center">
<img src="https://img.icons8.com/dusk/64/FA5252/lambda.png"/>
</div>

# FEID Lambda

Official collection of Lambda Formula by FIAKO Engineering.

Repository: [fiakoenjiniring/feidlambda](https://github.com/fiakoenjiniring/feidlambda)

## Koleksi FEID Lambda

Saat ini hanya tersedia 1 koleksi fungsi lambda yaitu:

- `feid.utils` ([Official GIST](https://gist.github.com/taruma/60610672a9bd94724cba46f68b5614fa)): kumpulan fungsi dasar berupa _logic_ atau _utilities_ yang dapat digunakan untuk membangun fungsi lambda bersifat _"narrative"_. ```https://gist.github.com/taruma/60610672a9bd94724cba46f68b5614fa```

### Koleksi `feid.utils`

**v0.2** | [Demo/GIF seluruh fitur di v0.2](./feidlambda-v-0-2.md)

- `feid.utils.COMPAREVECTOR`: Memeriksa apakah setiap elemen di LEFT VECTOR merupakan bagian (_equal_ / `=`)  dari RIGHT VECTOR. Return: BOOLEAN VECTOR.
- `feid.utils.MULTICHECK`: Memeriksa apakah pada setiap kolom _equal_ / `=` dengan SEARCH VECTOR. Dan diproses setiap barisnya menggunakan AND atau OR. Return: BOOLEAN VECTOR. 
- `feid.utils.DROPROWS`: Menghapus baris ke-n dari suatu array. Return: ARRAY. 
- `feid.utils.DROPCOLS`: Menghapus kolom ke-n dari suatu array. Return: ARRAY. 
- `feid.utils.COUNTMULTICHECK`: Menghitung jumlah `TRUE` dari `MULTICHECK`. Return: INTEGER.

**v0.1** | [Demo/GIF seluruh fitur di v0.1](./feidlambda-v-0-1.md)

- `feid.utils.REPEATCOLS`: Mengulangi _vector_ sebanyak _n_ dalam bentuk kolom. RETURN: COLUMN VECTOR. `=feid.utils.REPEATCOLS(vector, [num_repeat])`
- `feid.utils.SORTCOLS`: Menyusun ulang setiap kolom, berdasarkan _header_ yang diurutkan. RETURN: ARRAY. `=feid.utils.SORTCOLS(table, table_header)`
- `feid.utils.REPEATROWS`: Serupa dengan `.REPEATCOLS`, mengulangi _vector_ sebanyak _n_ dalam bentuk baris. RETURN: ROW VECTOR. `=feid.utils.REPEATROWS(vector, [num_repeat])`
- `feid.utils.RESHAPECOLS`: Mengubah dimensi array dari dimensi _rows x columns_, menjadi _rows x nsplit_ tanpa mengubah susunan baris array. RETURN: ARRAY. `=feid.utils.RESHAPECOLS(array, [nsplit])`
- `feid.utils.FINDINDEX2D`: Mencari index suatu elemen dari _array_ dengan keluaran berupa urutan elemennya, posisi baris dan kolom. RETURN: ARRAY / ROW VECTOR with {number element; index row; index column}. `=feid.utils.FINDINDEX2D(lookup_value, array)`

## Instruksi instalasi FEID Lambda

1. _Install_ add-ins **Advanced Formula Environment**.

![image](https://user-images.githubusercontent.com/1007910/192432581-0fd50e59-b0d0-4d9a-b802-81fda91060cb.png)

2. Buka **Advanced Formula Environment** (AFE) dan klik _import_. 

![image](https://user-images.githubusercontent.com/1007910/192432838-0b21184d-f06b-4d03-b56b-97aad0cd392a.png)

3. Isi "Github Gist URL" dengan link _Official GIST_ dari koleksi yang tersedia. Dan gunakan centang ✅ pada bagian "Add formulas to new namespace". Isi _namespace_ dengan nama koleksi. 

![image](https://user-images.githubusercontent.com/1007910/192433790-edfb2237-dcc9-41e0-898e-36d8a58e3438.png)

Lakukan langkah import (3-4) untuk setiap koleksi yang ingin ditambahkan. 

4. Setelah _import_ seluruh koleksi yang digunakan, lakukan _synchronize_ yang bertujuan menyamakan nama pada _Formula/Excel Name Manager_. 

![image](https://user-images.githubusercontent.com/1007910/192444394-78f8a5d5-1015-45b7-91e9-57f77f86923f.png)

Jika muncul peringatan mengenai _hidden sheets_, pilih "Allow ...". 

5. Selesai _sync_, fungsi lambda bisa langsung digunakan. 

![image](https://user-images.githubusercontent.com/1007910/192445916-242b2745-110b-44f7-9332-245b7fdaa695.png)

---

### LISENSI

- Proyek @fiakoenjiniring/feidlambda dan seluruh kodenya menggunakan MIT License. 
- <a target="_blank" href="https://icons8.com/icon/65815/lambda">Lambda</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>.
