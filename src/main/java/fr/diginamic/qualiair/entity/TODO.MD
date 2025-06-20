## Forum

~~Si on conserve l'historique des modifications, plus de sens de passer par une many to many que notre design erd :~~

~~  evite des fkey sans trop de sens

- ex de table Utilisateur_Modification_Message (id message, id utilisateur, date)~~

modifier erd avec nom rubrique

## Mesures

- Type de stratégie héritage, single/joined ? pour le moment joined
- mesure proba inutile

## Localités

- conserver a la fois num dept et code dept?

## coordonees

ajout d'un id unique pour coordonnee (bizarre à coder de les mettres en @embeddedId je pense?)

mappage many to one coord <- commune

## META

ajout des nullables et indexes

## Utilisateur

Modification erd pour ajouter motDePasse
