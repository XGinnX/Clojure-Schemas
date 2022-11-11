(ns hospital_sales.aula5
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))

(def Plano [s/Keyword])

(def Paciente
  {:id PosInt,
   :nome s/Str,
   :plano Plano
   ;Definir chave Opcional
   (s/optional-key :nascimnto) s/Str })

(def Pacientes
  {PosInt Paciente})

(def Visitas
  {PosInt [s/Str]})

;Foi removido o  IF e o THROW, pois a validação foi ativado dentro do def Paciente
;Nil não é um Pós Int
(s/defn adiciona-paciente :- Pacientes
  [pacientes :- Pacientes, paciente :- Paciente]
  (let [id (:id paciente)]
    (assoc pacientes id paciente)
  ))

(s/defn adiciona-visita :- Visitas
  [visitas :- Visitas, paciente :- PosInt, novas-visitas :- [s/Str] ]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-de-paciente
  [visitas :- Visitas, paciente :- PosInt]
  (println "Visitas do paciente" paciente "são" (get visitas paciente))
  )

(defn testa-uso-de-pacientes []
  (let [jean {:id 26, :nome "Jean", :plano []}
        marina {:id 02, :nome "Marina", :plano [:Pneumologista, :Dermatologista]}
        renata {:id 19, :nome "Renata", :plano [:Dermatologista]}
        pacientes (reduce adiciona-paciente {} [jean, marina, renata])
        visitas {}
        visitas (adiciona-visita visitas 26 ["01/01/2022"])
        visitas (adiciona-visita visitas 02 ["01/02/2022","07/02/2022"])
        visitas (adiciona-visita visitas 19 ["01/03/2022"])]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-de-paciente visitas 26)))

(testa-uso-de-pacientes)
