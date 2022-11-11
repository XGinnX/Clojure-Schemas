(ns hospital_sales.aula4
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


(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano [:raio-x, :ultrassom]}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano [:raio-x]}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano []}))
(pprint (s/validate Paciente {:id 19, :nome "Renata", :plano nil}))

(def Pacientes
  {PosInt Paciente})

(let [renata {:id 19, :nome "Renata", :plano [:raio-x, :ultrassom]}]
  (pprint (s/validate Pacientes {19 renata} )))

;Mapas podem representar entidades, a cada novo mapa que representa um Usuario as mesmas chaves são usadas.
;Ou podem representar coleções indexadas por um tipo de chave, de forma dinâmica. A chave pode ser, por exemplo, ids.