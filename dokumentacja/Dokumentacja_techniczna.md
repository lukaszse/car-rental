# 1. Wymagania systemowe
Aplikacja napisana została w wielopratformowym języku Java. Działa na każdym systemie z systemem operacyjnym Windows, Linux czy MacOS. 
Poniżej przedstawiono szczegółowe wymaganaia systemowe.

Wymagania systemowe:
* System operacyjny Windows 10/11, MacOs, Linux oraz inne systemy z rodziny Unix.
* Zainstalowana maszyna wirtualna Javy w wersji minimum 17. Zalecana dystrybucja OpenJDK 17.
* Opcjonalnie zainstalowane oprogramowanie Docker. Zalecana wersja 20.10.10 lub wyższa.

# 2. Deployment aplikacji
## 2.1. Z wykorzystaniem pliku jar
Skopiowac plik na serwer oraz uruchomić komendę:

`java -jar nazwa_pliku.jar`

## 2.2. Z wykorzystaniem obrazu Docker
Aby ściągnąć obraz Dockera zawierający aplikację należy użyć kolejno komend:

`docker pull llseremak/car-rental`
aby pobrać obraz, a następnie:

`docker run -d --restart unless-stopped -p 443:443 llseremak/car-rental`

gdzie pierwszy port 443 to port pod którym aplikacja będzie dostępna z zewnątrz kontenera (port 443 jest portem domyślnym da połączeń szyfrowanych z wykorzystaniem TSL)

## 2.3. Dodatkowa konfiguracja z wykrzystaniem NGINX
Przy pomocy NGINX można skonfigurowac przekierowanie z portu na którym działa aplikacja do okreslonego adresu url.
Dokumentacja NGINX: http://nginx.org/en/docs/