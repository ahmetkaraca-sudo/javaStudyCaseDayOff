# İzin Modülü

**Compile - Run**

Dizinde bulunan **"run.sh"** dosyasının yaptığı işlemler:

- Spring boot uygulamasını build eder.
- Spring boot uygulamasının ürettiği jar dosyasından docker image oluşturur.
- "docker-compose up -d" komutu ile phpmyadmin, mysql ve localde oluşan image'ı ayağa kaldırır.

Gerekli linkler:

- Swagger UI: <http://localhost:8080/swagger-ui.html>
- phpmyadmin: <http://localhost:8888>
  - Kullanıcı Adı: root
  - Kullanıcı Parola: root

**End-point:**

Kullanıcı işlemleri için herhangi bir role ihtiyaç yoktur.

Ön tanımlı kullanıcılar:

1. MANAGER: 
   1. Kullanıcı Adı: ahmet
   1. Parola: seyit
1. USER:
   1. Kullanıcı Adı: seyit
   1. Parola: seyit

**Login olup token alma:** 

- <http://localhost:8080/swagger-ui.html#/user-controller/loginUsingPOST> adresine gidilir.
- İstenilen kullanıcı adı ve parolası girilir ve response’taki token alınır.

Alınan token Day-Off-Controller (<http://localhost:8080/swagger-ui.html#/day-off-controller>)'daki endpointlere erişebilmek için kullanılır. 

Bu endpointteki bir metodun en sağında bulunan KİLİT işaretine tıklanır ve "Value" ile belirtilen alana token yazılır.

"Authorize" butonuna tıklandıktan sonra endpointlere yapılan istekler bu token ile gönderilir.



- İzin Talebi (<http://localhost:8080/swagger-ui.html#/day-off-controller/requestDayOffUsingPOST>)

Request metodu ile izin talebi açılır. İzin başlangıç ve bitiş tarihi belirtilir. 

Formatı "yyyy-mm-dd" şeklinde olmalıdır.

- İzin Onayı (<http://localhost:8080/swagger-ui.html#/day-off-controller/approveDayOffUsingPOST>)

Approve metodu ile açılmış talepler onaylanır. 

Talep id'si ve bir durum ("ACCEPTED", "PENDING", "DENIED") girilir.

Sadece MANAGER rolüne sahip kullanıcılar bu endpointe istek atabilir.

- İzin taleplerinin görüntülenmesi (<http://localhost:8080/swagger-ui.html#/day-off-controller/getAllDayOffUsingGET>)

Açılmış izin taleplerini durumları ile görüntüler.

**Code Coverage:**

![code_coverage_img](https://user-images.githubusercontent.com/62589488/109430175-01e8d180-7a11-11eb-9e5a-60a861f1f518.jpg)


Login ve JWT olan kodlara test yazılamadı. Sadece bir endpointe test yazıldı, code coverage’ı çok fazla etkilemeyeceği için diğerlerine yazılmadı. Görüldüğü gibi servislere test yazılmaya ağırlık verildi.  

**Kurallar:**

- Hafta sonu (Cumartesi, Pazar) ve resmi tatil günleri izin süresinden sayılmaz.

*2020 ve 2021 resmi tatil günleri veri tabanında tutuldu. Resmi tatil günleri ve hafta sonları izin günlerine eklenmedi.*

- Yeni işe başlayan kişiler avans olarak 5 iş günü kadar izin kullanabilir. Bu uygulama sadece ilk yıl için geçerlidir.

*Geçerli tabloya uygun izin günleri tanımlandı. İşe yeni başlayan çalışanın ilk yılı içerisinde 5 gün avans tanımlandı.*

*Çalışılmaya başlandığı tarihten itibaren yıllık olarak izinler tanımlandı.*

*Bir önceki yılın izni yeni yıla devredilmedi.*

- İleri tarihli bir izin talebi yapıldığında, yapılan gün sayısı çalışanın hak ettiği izin gün sayısından düşer.

*İzinler sadece izin alınacak günden sonraya girilebiliyor. Geçmişe dönük izin girilemiyor.*

- Çalışan izin girişi yaparken, kalan izin gün sayısından fazla izin talep edemez.

*Çalıştığı zaman süresinde tanımlanan izin süresinden fazlasını talep edemiyor.*

- İzin talepleri yaratıldığı zaman “Onay Bekleniyor” statüsünde oluşur, yönetici onayından geçtikten sonra “Onaylandı” veya “Reddedildi” statülerine düşer.

*İzin talepleri PENDING, DENIED ve ACCEPTED olarak üç farklı durumda tutuluyor.*

*İzin talebi girildiğinde PENDING olarak tanımlanıyor. Sadece yönetici rolündeki kullanıcı, talebi onaylayabiliyor.*
=======
# javaStudyCaseDayOff

Dizinde bulunan "run.sh" dosyasının yapıtığı işlemler;
  Spring boot uygulamasını build eder.
  Spring boot uygulamasının ürettiği jar dosyasından docker image oluşturur..
  "docker-compose up -d" komutu ile de phpmyadmin, mysql ve localde oluşan image'ı ayağa kaldırır.

Gerekli linkler:
Uygulama swagger ui linki: http://localhost:8080/swagger-ui.html
phpmyadmin linki: http://localhost:8888


db kullanıcı adı: root
db kullanıcı parola: root

End-pointlerin kullanımı:
  kullanıcı işlemleri için herhangi bir role ihtiyaç yoktur.

  Öntanımlı kullanıcılar 
   1) MANAGER: kullanıcı adı: ahmet ,parola: seyit
   2) USER: kullanıcı adı: seyit ,parola: seyit

  http://localhost:8080/swagger-ui.html#/user-controller/loginUsingPOST adresine gidilir.
  USER rolune sahip kullanıcı adı ve parolası girilir ve token alınır.

  Alınan token Day-Off-Controller (http://localhost:8080/swagger-ui.html#/day-off-controller)'daki endpointleri kullanabilmek için
  kullanılır. Token bu endpointteki bir methodun en sağında bulunan KİLİT işaretine tıklanır ve "Value" ile belirtilen alana yazılır.
  "Authorize" butonuna tıklandıktan sonra endpointlere yapılan istekler bu token ile gönderilir.

  
  izin talebi: (http://localhost:8080/swagger-ui.html#/day-off-controller/requestDayOffUsingPOST)
  request methodu ile izin talebi açılır. İzin başlangıç ve bitiş tarihi belirtilir. Formatı "yyyy-mm-dd" şeklinde olmalıdır.


  izin onayı: (http://localhost:8080/swagger-ui.html#/day-off-controller/approveDayOffUsingPOST)
  approve methodu ile açılmış talepler onaylanır. Talep id'si ve bir durum ("ACCEPTED", "PENDING" , "DENIED") girilir.
  Sadece MANAGER rolune sahip kullanıcılar bu endpointe istek atabilir.

  
  İzin taleplerinin görüntülenmesi: (http://localhost:8080/swagger-ui.html#/day-off-controller/getAllDayOffUsingGET)
  Açılmış izin taleplerini durumları ile görüntüler.


Kurallar
- Hafta sonu (Cumartesi, Pazar) ve resmi tatil günleri izin süresinden sayılmaz.
  2020 ve 2021 resmi tatil günleri veritabanında tutuldu. Resmi tatil günleri ve haftasonları izin günlerine eklenmedi.
- Yeni işe başlayan kişiler avans olarak 5 iş günü kadar izin kullanabilir. Bu uygulama sadece ilk yıl için
geçerlidir
  Geçerli tabloya uygun izin günleri tanımlandı. İşe yeni başlayan çalışanın ilk yılı içerisinde 5 gün avans tanımlandı.
  Çalışılmaya başlandığı tarihten itibaren yıllık olarak izinler tanımlandı.
  Bir önceki yılın izni yeni yıla devredilmedi.
- İleri tarihli bir izin talebi yapıldığında, yapılan gün sayısı çalışanın hak ettiği izin gün sayısından
düşer.
  izinler sadece izin alınacak günden sonraya girilebiliyor. Geçmişe dönük izin girilemiyor.
- Çalışan izin girişi yaparken, kalan izin gün sayısından fazla izin talep edemez.
  Çalıştığı zaman süresinde tanımlanan izin süresinden fazlasını talep edemiyor.
- İzin talepleri yaratıldığı zaman “Onay Bekleniyor” statüsünde oluşur, yönetici onayından geçtikten
sonra “Onaylandı” veya “Reddedildi” statülerine düşer.
  İzin talepleri PENDING, DENIED ve ACCEPTED olarak üç farklı durumda tutuluyor.
  İzin talebi girildiğinde PENDING olarak tanımlanıyor. Sadece yönetici rolündeki kullanıcı, talebi onaylayabiliyor.




