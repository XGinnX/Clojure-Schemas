(ns hospital_sales.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;(s/defrecord Paciente
;[id :- Long, nome :- s/Str])

;(pprint (Paciente. 26 "Jean"))
; O defrecord é expansivo, aceita mais valores do que esperado
;(pprint (map->Paciente {26 "Jean"}))
;(pprint (map->Paciente {26 "Jean"}))

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})
; Explica o Schema
(pprint (s/explain Paciente))

(pprint (s/validate Paciente {:id 26, :nome "jean"}))
(pprint (s/validate Paciente {:id 02, :nome "marina"}))
(pprint (s/validate Paciente {:id 19, :nome "renata"}))

; Typo é pego pelo schema, esse modelo de erro seria pego em testes automatizados.
;(pprint (s/validate Paciente {:id 19, :name "renata"})) ;name não é uma chave.
; A questão é se você deseja que seja forward compatible ou não. Entender o custo/beneficio
;Forward - Compativel para novas informações

;Reclama um erro por naão estar com as chaves necessaárias.
;(pprint (s/validate Paciente {:id 19}))

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 19 "Minerva"))

(defn estritamente-positivo? [x]
  (> x 0))

;(def EstritamentePositivo (s/pred estritamente-positivo?))
(def EstritamentePositivo (s/pred estritamente-positivo? 'estritamente-positivo'))

(pprint (s/validate EstritamentePositivo 15))

(def Paciente
  "Schema de um paciente"
  {:id (s/constrained s/Int pos?), :nome s/Str})

;(def Paciente
;  "Schema de um paciente"
;  {:id (s/constrained s/Int estritamente-positivo?), :nome s/Str})

(def Paciente
  "Schema de um paciente"
  {:id (s/constrained s/Int #(> % 0) 'inteiro-estritamente-positivo), :nome s/Str})

(pprint (s/validate Paciente {:id -02, :nome "marina"}))

;Podemos usar predicates com Schemas para Definir regras extras que limitam os valores de nossos dados. Cada predicado limita os valores de um dado.