# Subscriber Service

## Deskripsi
Subscriber Service adalah layanan backend yang menangani manajemen data subscriber, termasuk pengecekan prefix Virtual Account (VA) menggunakan Redis dan TreeMap, serta integrasi dengan database relasional.

Service ini dibangun dengan menggunakan Java Spring Boot dan Redis sebagai cache untuk meningkatkan performa pencarian prefix VA.

## Fitur Utama
- Pengecekan prefix Virtual Account secara cepat menggunakan Redis dan TreeMap.
- Penyimpanan dan pengambilan data BillerCategory, BillerPrefix, dan BillerProduct dari database.
- API endpoint untuk validasi prefix VA.
- Integrasi dengan Redis untuk caching data prefix.
- Struktur modular yang mudah dikembangkan.

## Teknologi
- Java 17
- Spring Boot 3.x
- Redis
- JPA / Hibernate
- Maven

## Struktur Project
- `src/main/java` - kode sumber aplikasi
- `src/main/resources` - konfigurasi dan resource
- `src/test` - unit test
- `pom.xml` - manajemen dependensi Maven

## Persiapan Environment
1. Pastikan Java 17 sudah terinstall dan terkonfigurasi dengan benar di PATH.
2. Jalankan Redis server di port default (6379) atau sesuaikan konfigurasi di `application.properties`.
3. Pastikan database (PostgreSQL/MySQL) sudah berjalan dan akses kredensial sudah benar.

## Konfigurasi
Sesuaikan file `src/main/resources/application.properties` atau `application.yml` dengan konfigurasi database dan Redis Anda. Contoh minimal:

````
properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=youruser
spring.datasource.password=yourpassword

spring.redis.host=localhost
spring.redis.port=6379
````

## Cara Menjalankan

Build project dengan Maven:

bash
mvn clean package

Jalankan aplikasi:

````
java -jar target/subscriber-service-0.0.1-SNAPSHOT.jar
````

Akses API di: http://localhost:8080/

API Endpoint (Contoh)
````
POST /api/v1/check-prefix
````
Body:
````
{
  "vaNumber": "1234567890"
}
````
Response:
````
{
  "isValid": true,
  "category": "SomeCategory",
  "biller": "SomeBiller"
}
````

Testing
Untuk menjalankan unit test:
````
mvn test
````

## Kontribusi
Kontribusi sangat kami hargai!
Silakan buat branch baru dan kirim pull request dengan penjelasan perubahan yang jelas.


## Lisensi
Project ini menggunakan lisensi MIT.
Silakan cek file LICENSE untuk detail.
