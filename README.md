# Android-Network-Traffic-Logger

 AĞ TRAFİĞİ TESTİ RAPORU
1. Giriş
Bu rapor, Android işletim sistemine sahip bir cihazda ağ trafiğinin izlenmesi ve JMeter aracı ile oluşturulan yük testinin etkilerinin analiz edilmesi amacıyla hazırlanmıştır. Ağ trafiği, NetTrafficStats kullanılarak ölçülmüş ve JMeter ile belirlenen statik IP'ye HTTP istekleri gönderilerek trafik yükü oluşturulmuştur.
2. Metodoloji
2.1 Kullanılan Araçlar
Android Uygulaması: Arka planda çalışan bir servis ile ağ trafiğini kayıt altına almak için geliştirilmiştir.
 
Şekil 1. Ağ trafik loglarını tutan android uygulama 
NetTrafficStats API: Android cihazın aldığı ve gönderdiği veri miktarını ölçmek için kullanılmıştır.
JMeter: Android cihazın statik IP’sine sürekli HTTP istekleri göndererek trafik yükü oluşturulmuştur.
 
CSV Dosyası: Ölçülen veriler iki ayrı CSV dosyasına kaydedilerek analiz edilmiştir:
  1.normal_traffic.csv: Trafik yükü öncesi verileri içerir.
  2.increased_traffic.csv: JMeter ile trafik yükü sonrası verileri içerir.
2.2 Test Senaryosu
1. Ağ Trafiği İzlem: İlk aşamada, cihazda normal ağ trafiği izlenmiş ve "Received Bytes" ile "Transmitted Bytes" verileri kaydedilmiştir.
2. JMeter Yük Testi: 40 istemci kullanarak belirlenen IP adresine HTTP istekleri gönderilmiş ve trafik yükü oluşturulmuştur.
3. Yük Testi Sonrası Ağ Trafiği İzleme: JMeter’in çalıştırılmasının ardından tekrar "Received Bytes" ve "Transmitted Bytes" değerleri ölçülmüştür.
3. Veri Analizi
Yapılan testler sonucunda aşağıdaki bulgular elde edilmiştir:
	JMeter yükü öncesinde alınan (Received Bytes) ve gönderilen (Transmitted Bytes) veri miktarı düşük seviyedeydi.
	JMeter yükü sırasında bu değerlerde gözle görülür bir artış meydana gelmiştir.
	Grafik analizleri, yük testi sonrası veri akışında önemli bir artış olduğunu göstermektedir.

3.1 Grafiksel Analiz
Aşağıdaki grafik, belirli zaman aralıklarında "Received Bytes" ve "Transmitted Bytes" değerlerinin nasıl değiştiğini göstermektedir.
 
Şekil 2. Grafik, zaman içinde alınan ve gönderilen baytların (Received Bytes ve Transmitted Bytes) değişimini göstermektedir. JMeter yük testi sırasında, özellikle alınan (Received) ve gönderilen (Transmitted) bayt değerlerinde bir artış gözlemleniyor.
4. Kod Açıklaması ve Yöntem
	Servis Uygulaması: Android servis arka planda çalışarak ağ trafiğini izler ve kaydeder.
	Statik IP Kullanımı: Testler sırasında statik IP adresi kullanılmıştır.
	-JMeter ile Trafik Simülasyonu 40 istemci belirlenen IP’ye yük bindirerek ağ trafiği artırılmıştır.
5. Sonuç ve Öneriler
Bu test sonucunda, JMeter ile oluşturulan trafik yükünün, Android cihazın ağ trafiği ölçümlerinde kayda değer bir etkiye sahip olduğu görülmüştür.
5.1 Sonuçlar
	JMeter yük testi sırasında ağ trafiği önemli ölçüde artmıştır.
	Veri analizleri, yük testiyle cihazın daha fazla veri alışverişinde bulunduğunu doğrulamaktadır.**
	Android cihazın statik IP adresine yapılan yoğun HTTP istekleri, NetTrafficStats tarafından kaydedilmiş ve CSV dosyasına işlenmiştir.
6. Teslim Edilecek Dosyalar
-Android Uygulama Kaynak Kodu: Açıklamalı olarak paylaşılan tüm kodlar.

CSV Dosyaları:
normal_traffic.csv (Test öncesi ağ trafiği verileri)
increased_traffic.csv (JMeter yükü sonrası veriler)
  Bu Rapor: Test süreci ve sonuçları içeren detaylı dokümantasyon.

Sonuç
**Proje başarıyla tamamlanmıştır ve ağ trafiğinin test edilmesi için önerilen yöntemler uygulanmıştır.**

