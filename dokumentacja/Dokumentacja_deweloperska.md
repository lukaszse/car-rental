# 1. Repozytorium

## 1.1. Wymagania wstępne
Aby skorzystac z repozorium należy zainstalować na lokalnej maszynie aplikację aplikację do kontroli wersji.
Apikacja jest dostępna dla systemów Windows, Linux oraz MacOS:
https://git-scm.com/

## 1.2. Pobieranie repozytorium
Kod aplikacji znajduje się w repozytrium w serwisie GitHub:
https://github.com/lukaszse/car-rental

W celu pobrania repozytorium, użyj komendy:

`git clone https://github.com/lukaszse/car-rental.git`

## 1.3. Uruchamianie aplikacji
Aby uruchomić aplikację należy zbudować plik jak. W tym celu nalezy uruchomić komendę:

`./mvnw clean install`

# 2. Obraz Docker
W głównym folderze aplikacji znajduje się plik `Dockerfile` który służy do zbudowania obrazu z aktualnego pliku jar znajdującego się w foldwrze `target`.
Aby zbudowac obraz nalezy użyć komendy:

`docker build -t nazwaObrazu .`

W celu zapisania obrazu w repozytorium zdalnym Dockera, tak aby był dostępny online dla innych maszyn należy użyć komendy:
`docker push nazwaObrazu`