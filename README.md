# 🧩 OptimizationAlgorithm

Bu repository, **Heuristic (Sezgisel) ve Metaheuristic (Üst Sezgisel) Optimizasyon Algoritmaları** hakkında temel bilgiler, algoritma açıklamaları ve örnek uygulamaları içermektedir. Özellikle **Local Search** ve **Simulated Annealing (SA)** gibi yöntemler üzerine odaklanılmıştır.

---

## 📌 İçerik

- [Giriş](#giriş)
- [Optimizasyon Algoritmaları Nedir?](#optimizasyon-algoritmaları-nedir)
- [Yerel Arama (Local Search)](#yerel-arama-local-search)
- [Simulated Annealing (SA)](#simulated-annealing-sa)
- [Kullanım Alanları](#kullanım-alanları)
- [Kurulum & Kullanım](#kurulum--kullanım)
- [Kaynaklar](#kaynaklar)

---

## 🎯 Giriş

Optimizasyon problemleri; bir hedef (cost, distance, time vb.) fonksiyonu altında en iyi çözümün bulunmasını amaçlayan problemlerdir. Özellikle çözüm alanı büyük ve karmaşık olduğunda, klasik yöntemler yetersiz kalabilir. Bu durumda **Heuristic** ve **Metaheuristic** algoritmalar devreye girer.

---

## ❓ Optimizasyon Algoritmaları Nedir?

**Optimizasyon Algoritmaları**, belirli kısıtlar altında bir hedef fonksiyonu **maksimize** veya **minimize** etmeye çalışan algoritmalardır.  
Bu algoritmalar iki ana kategoriye ayrılır:

- **Deterministic Algorithms:** Matematiksel kesinlik sunar. Örn: Linear Programming
- **Heuristic & Metaheuristic Algorithms:** Daha hızlı, pratik, ama kesin optimum garantisi yoktur.

Bu projede özellikle ikinci kategori ele alınmıştır.

---

## 🔍 Yerel Arama (Local Search)

Yerel arama algoritmaları, mevcut bir çözümden başlayarak **komşu çözümler** arasında daha iyi bir çözüm bulmaya çalışır.

### Özellikleri:
- **Hızlı ve basit** uygulama
- Çözüm alanında sıkışıp kalma (local optimum) riski vardır
- Genellikle greedy yaklaşım

### Yapısı:
1. Başlangıç çözümü oluştur
2. Komşu çözümler arasında iyileştirme ara
3. İyileştirme yoksa dur, varsa devam et

---

## ❄️ Simulated Annealing (SA)

**Simulated Annealing**, doğadaki **ısıl tavlama (annealing)** sürecinden ilham alan bir metaheuristic algoritmadır.  
1983 yılında Kirkpatrick, Gelatt ve Vecchi tarafından önerilmiştir.

### Temel Mantık:
Bir çözümden rastgele komşuya geçilir:
- Daha iyi bir çözüme **her zaman geçilir**.
- Daha kötü bir çözüme ise, **belli bir olasılıkla (sıcaklığa bağlı) geçilir**.  
  ➤ Bu sayede **local optimumdan çıkabilir**.

### Yapısı:

```plaintext
1. Başlangıç çözümü ve sıcaklık belirlenir (t0)
2. Belirli iterasyon sayısı kadar:
    a. Rastgele komşu çözüm seç
    b. İyileştiriyorsa kabul et
    c. Kötüyse, belirli olasılıkla kabul et (exp(-Δ/t))
3. Sıcaklık düşürülür (Cooling Schedule)
4. Sıcaklık belli eşik değerin altına düştüğünde algoritma durur
