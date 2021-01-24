# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Burdîniuc Ilie, 321CD

Github: https://github.com/burdyniuk/Proiect-POO-Etapa2

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

## Implementare

### Entitati

- Pachetul common:

    - contine o clasa Constants, care pastreaza constantele folosite in calcule

- Pachetul input:

    - contine clasele pe care le foloseste biblioteca jackson pentru citirea
      datelor din fisierele json

- Pachetul strategies:
    
    - contine interfata ChooseStrategy si clasele, folosite pentru implementarea strategiilor Green, Price, Quantity, 
    dar si enum cu tipurile acestora
    
- Pachetul utils:

    - contine metoda pentru calcularea initiala a contractului in runda 0

- Pachetul output:

    - contine clasele care pastreaza si prelucreaza datele, acestea vor fi
      scrise in fisierul result.out
    - clasa OutElement e o interfata pe care le implementeaza Consumer si
      Distributor
    - clasa Consumer contine metodele pentru primirea salariului si achitarea
      contractului
    - clasa Distributor contine metodele pentru primirea platii contractului,
      achitarea cheltuielilor si calcularea numarului de contracte neterminate
    - clasa OutputFactory contine factory pentru crearea unui din element din
      output, la fel aici se foloseste singleton pentru a instantia o singura
      instanta factory
    - clasa Contract defineste obiectul contract cu parametrii necesari,
      folositi in achitarea si calcularea contractelor
    - clasa EnergyProducer e clasa ce reprezinta producatorul si toate proprietatile ecesteia, la fel face si statistica lunara.
    - clasa MonthlyStats e clasa ce reprezinta aceste statistici.
    - clasa Observator e interfata folosita pentru implementarea observatorilor, contine doar metoda update.

- Main:

    - clasa responsabila pentru simulare, aici e descris flow-ul.

### Flow

Clasa principala in simulare e Main: 
- creeaza arraylist-ele de distributor, consumers si producers din pachetul output
- alege producatorii pentru distribuitori folosind strategiile
- alege contractele initiale (din runda 0) si le adauga in listele
de contracte a claselor distributor
- simuleaza fiecare luna a jocului
- recalculeaza contractele
- gaseste noile preturi a distributor-ilor si le insereaza in clasa
- cauta contractele care au expirat si creaza noile cu cel mai bun
pret, apoi sterge contractele care s-au terminat din distribut-orii
- se efectueaza platile necesare
- se instantiaza clasa necesara de output, se insereaza listele si se
scrie in fisierul de out

### Design patterns

Am descris doar cele noi adaugate fata de etapa 1.

- Observer

Contract e observer si Distributor e observable. Distributor contine un 
ArrayList de Contracte, si cand distributor plateste costurile se face update pe lunile ramase ale contractului, iar daca
nu au ramas luni se termina contractul. 

- Strategy

Am creat pachet cu strategii, in baza carora distribuitorii aleg producatorii de la care vor cumpara energia, pentru 
ca fiecare distribuitor are cerintele sale fata de producatori.