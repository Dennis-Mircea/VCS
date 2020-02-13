# VCS
Simulated the usage of Linux mini-shell(a simplified versioning system)

In rezolvarea temei am creat cate o clasa pentru fiecare comanda,
adaugand de asemenea in OperationType, pe rand, numele simbolic al fiecarei
comenzi, completand si OperationParser si OperationFactory cu comenzile
necesare, astfel incat rularea fiecarei comenzi sa realizeze cu succes.

Clasele ce reprezinta comenzi sunt(acestea exitind clasa VcsOperation):

-> StatusOperation - implementeaza comanda "vcs status"

-> CreataBranchOperation - implementeaza comanda "vcs branch"

-> CommitOperation - implementeaza comanda "vcs commit 'mesaj'"

-> CheckoutOperation - implementeaza comenzile "vcs checkout
		branchName" si "vcs checkout -c commitId"

-> LogOperation - implementeaza operatia "vcs log"

-> RollbackOperation - implementeaza comanda "vcs rollback"

-> InvalidOperation - cazul in care vcs primeste o comanda invalida

Aceste clase asociate comenzilor se apeleaza atunci cand din clasa
Context se apeleaza metoda visit cu parametrul vcs.
	Clasei de baza pentru comenzile vcs (clasa Vcs) i-am adaugat campurile:
		-> currentBranch - sa stim mereu branch-ul pe care ne aflam(acolo unde
		pointeaza mereu HEAD-ul);
		-> allBranches - retine toate branch-urile pe care se poate duce
		un utilizator;
		-> currentStage - retine stage-ul curent
		-> head - retine ultimul commit pe care pointeaza pointerul head
	De asemenea, in clasa Vcs am completat metoda visit si am mai implementat
alte metode cu care aceasta interactioneaza cu celelalte clase.
	In clasa Context, am tratat cazul in care o camanda de filesystem este
isTrackable	si am actualizat stage-ul.


Alte clase importante care sunt utile in rezolvarea comenzilor:
		-> Commit - reprezinta efectiv commit-ul ce se creaza in urma
		operatiei de commit, continand un id, un mesaj, si stage-ul
		salvat;
		-> Branch - reprezinta un branch cu toate campurile necesare,
		continand un nume, toate commit-urile ce au fost data pe acel
		branch;
		-> Staging - reprezinta stage-ul curent, care retine toate
		entitatile au fost create pana punctul curent si un content
		care va fi afisat in momentul comenzii status;
