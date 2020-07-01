﻿(define (problem Ejercicio6-Problema)

    (:domain Ejercicio6-Dominio)
    
    (:objects
        l1_1 l1_2 l1_3 l1_4 l1_5 l2_1 l2_2 l2_3 l2_4 l2_5 l3_1 l3_2 l3_3 l3_4 l3_5 l4_1 l4_2 l4_3 l4_4 l4_5 l5_1 l5_2 l5_3 l5_4 l5_5 - Localizacion
        
        ; Centro de mando inicial
        centro - Edificio
        ; Barracon a utilizar
        barracon - Edificio
        ; Extractores para situarlos encima del gas
        extractor1 extractor2 -Edificio
        ; Bahías
        bahia - Edificio
        ; Depósitos a construir
        deposito1 deposito2 deposito3 - Edificio
        ; vce1 Existirá siempre, vce2..4 Se podrán generar.
        vce1 vce2 vce3 vce4 - Unidad
        
        
        marine1 marine2 segador1 - Unidad
        
        gas1 gas2 - Recurso
        minerales1 minerales2 minerales3 - Recurso
        
        impulsor_segador - Investigacion
        
    )
    
    (:init
    
        ; Mapa generado por el pluggin de planning.domains
        (Camino l1_1 l2_1)
        (Camino l2_1 l1_1)
        (Camino l1_1 l1_2)
        (Camino l1_2 l1_1)
        (Camino l1_2 l2_2)
        (Camino l2_2 l1_2)
        (Camino l1_2 l1_3)
        (Camino l1_3 l1_2)
        (Camino l1_2 l1_1)
        (Camino l1_1 l1_2)
        (Camino l1_3 l2_3)
        (Camino l2_3 l1_3)
        (Camino l1_3 l1_4)
        (Camino l1_4 l1_3)
        (Camino l1_3 l1_2)
        (Camino l1_2 l1_3)
        (Camino l1_4 l2_4)
        (Camino l2_4 l1_4)
        (Camino l1_4 l1_5)
        (Camino l1_5 l1_4)
        (Camino l1_4 l1_3)
        (Camino l1_3 l1_4)
        (Camino l1_5 l2_5)
        (Camino l2_5 l1_5)
        (Camino l1_5 l1_4)
        (Camino l1_4 l1_5)
        (Camino l2_1 l1_1)
        (Camino l1_1 l2_1)
        (Camino l2_1 l3_1)
        (Camino l3_1 l2_1)
        (Camino l2_1 l2_2)
        (Camino l2_2 l2_1)
        (Camino l2_2 l1_2)
        (Camino l1_2 l2_2)
        (Camino l2_2 l3_2)
        (Camino l3_2 l2_2)
        (Camino l2_2 l2_3)
        (Camino l2_3 l2_2)
        (Camino l2_2 l2_1)
        (Camino l2_1 l2_2)
        (Camino l2_3 l1_3)
        (Camino l1_3 l2_3)
        (Camino l2_3 l3_3)
        (Camino l3_3 l2_3)
        (Camino l2_3 l2_4)
        (Camino l2_4 l2_3)
        (Camino l2_3 l2_2)
        (Camino l2_2 l2_3)
        (Camino l2_4 l1_4)
        (Camino l1_4 l2_4)
        (Camino l2_4 l3_4)
        (Camino l3_4 l2_4)
        (Camino l2_4 l2_5)
        (Camino l2_5 l2_4)
        (Camino l2_4 l2_3)
        (Camino l2_3 l2_4)
        (Camino l2_5 l1_5)
        (Camino l1_5 l2_5)
        (Camino l2_5 l3_5)
        (Camino l3_5 l2_5)
        (Camino l2_5 l2_4)
        (Camino l2_4 l2_5)
        (Camino l3_1 l2_1)
        (Camino l2_1 l3_1)
        (Camino l3_1 l4_1)
        (Camino l4_1 l3_1)
        (Camino l3_1 l3_2)
        (Camino l3_2 l3_1)
        (Camino l3_2 l2_2)
        (Camino l2_2 l3_2)
        (Camino l3_2 l4_2)
        (Camino l4_2 l3_2)
        (Camino l3_2 l3_3)
        (Camino l3_3 l3_2)
        (Camino l3_2 l3_1)
        (Camino l3_1 l3_2)
        (Camino l3_3 l2_3)
        (Camino l2_3 l3_3)
        (Camino l3_3 l4_3)
        (Camino l4_3 l3_3)
        (Camino l3_3 l3_4)
        (Camino l3_4 l3_3)
        (Camino l3_3 l3_2)
        (Camino l3_2 l3_3)
        (Camino l3_4 l2_4)
        (Camino l2_4 l3_4)
        (Camino l3_4 l4_4)
        (Camino l4_4 l3_4)
        (Camino l3_4 l3_5)
        (Camino l3_5 l3_4)
        (Camino l3_4 l3_3)
        (Camino l3_3 l3_4)
        (Camino l3_5 l2_5)
        (Camino l2_5 l3_5)
        (Camino l3_5 l4_5)
        (Camino l4_5 l3_5)
        (Camino l3_5 l3_4)
        (Camino l3_4 l3_5)
        (Camino l4_1 l3_1)
        (Camino l3_1 l4_1)
        (Camino l4_1 l5_1)
        (Camino l5_1 l4_1)
        (Camino l4_1 l4_2)
        (Camino l4_2 l4_1)
        (Camino l4_2 l3_2)
        (Camino l3_2 l4_2)
        (Camino l4_2 l5_2)
        (Camino l5_2 l4_2)
        (Camino l4_2 l4_3)
        (Camino l4_3 l4_2)
        (Camino l4_2 l4_1)
        (Camino l4_1 l4_2)
        (Camino l4_3 l3_3)
        (Camino l3_3 l4_3)
        (Camino l4_3 l5_3)
        (Camino l5_3 l4_3)
        (Camino l4_3 l4_4)
        (Camino l4_4 l4_3)
        (Camino l4_3 l4_2)
        (Camino l4_2 l4_3)
        (Camino l4_4 l3_4)
        (Camino l3_4 l4_4)
        (Camino l4_4 l5_4)
        (Camino l5_4 l4_4)
        (Camino l4_4 l4_5)
        (Camino l4_5 l4_4)
        (Camino l4_4 l4_3)
        (Camino l4_3 l4_4)
        (Camino l4_5 l3_5)
        (Camino l3_5 l4_5)
        (Camino l4_5 l5_5)
        (Camino l5_5 l4_5)
        (Camino l4_5 l4_4)
        (Camino l4_4 l4_5)
        (Camino l5_1 l4_1)
        (Camino l4_1 l5_1)
        (Camino l5_1 l5_2)
        (Camino l5_2 l5_1)
        (Camino l5_2 l4_2)
        (Camino l4_2 l5_2)
        (Camino l5_2 l5_3)
        (Camino l5_3 l5_2)
        (Camino l5_2 l5_1)
        (Camino l5_1 l5_2)
        (Camino l5_3 l4_3)
        (Camino l4_3 l5_3)
        (Camino l5_3 l5_4)
        (Camino l5_4 l5_3)
        (Camino l5_3 l5_2)
        (Camino l5_2 l5_3)
        (Camino l5_4 l4_4)
        (Camino l4_4 l5_4)
        (Camino l5_4 l5_5)
        (Camino l5_5 l5_4)
        (Camino l5_4 l5_3)
        (Camino l5_3 l5_4)
        (Camino l5_5 l4_5)
        (Camino l4_5 l5_5)
        (Camino l5_5 l5_4)
        (Camino l5_4 l5_5)
        
        ; Añadir Centro de Mando Construido en el mapa
        (EsTipoE centro CentroDeMando)
        (EnE centro l3_3)
        (Construido centro)
        
        ; Asignar VCE en el mapa
        (EsTipoU vce1 VCE)
        (EnU vce1 l3_3)
        (Reclutada vce1)
        
        ; Asignar Recursos En el mapa
        (EsTipoR minerales1 Mineral)
        (Asignar minerales1 l1_1)
        
        (EsTipoR minerales2 Mineral)
        (Asignar minerales2 l2_1)
        
        (EsTipoR minerales3 Mineral)
        (Asignar minerales3 l3_1)
        
        (EsTipoR gas1 Gas)
        (EsTipoE extractor1 Extractor)
        (Asignar gas1 l5_3)
        (EnE extractor1 l5_3)
        
        (EsTipoR gas2 Gas)
        (EsTipoE extractor2 Extractor)
        (Asignar gas2 l4_3)
        (EnE extractor2 l4_3)
        
        ; Situamos El barracon a construir
        (EsTipoE barracon Barracon)
        (EnE barracon l2_1)
        
        ; Situamos La Bahía a construir
        (EsTipoE bahia Bahia_Ingenieria)
        (EnE bahia l3_4)
        
        ; Informacion Relativa a los elementos no reclutados
        (EsTipoU vce2 VCE)
        (EsTipoU vce3 VCE)
        (EsTipoU vce4 VCE)
        (EsTipoU marine1 Marine)
        (EsTipoU marine2 Marine)
        (EsTipoU segador1 Segador)
        
        ; Asignamos el material necesario para contruir cara edificio
        ; Como se indica en el foro, necesita minerales y gas la bahia
        (= (Necesita CentroDeMando Mineral) 150)
        (= (Necesita CentroDeMando Gas) 50)
        (= (Necesita Barracon Mineral) 150)
        (= (Necesita Barracon Gas) 0)
        (= (Necesita Extractor Mineral) 75)
        (= (Necesita Extractor Gas) 0)
        (= (Necesita Bahia_Ingenieria Mineral) 125)
        (= (Necesita Bahia_Ingenieria Gas) 0)
        (= (Necesita Deposito Mineral) 75)
        (= (Necesita Deposito Gas) 25)
        
        ; Depósitos
        (EsTipoE deposito1 Deposito)
        (EnE deposito1 l1_2)
        (EsTipoE deposito2 Deposito)
        (EnE deposito2 l2_2)
        (EsTipoE deposito3 Deposito)
        (EnE deposito3 l3_2)
        
        ; Asignamos el material necesario para contruir cada unidad
        (= (Requiere VCE Mineral) 50)
        (= (Requiere VCE Gas) 0)
        (= (Requiere Marine Mineral) 50)
        (= (Requiere Marine Gas) 0)
        (= (Requiere Segador Mineral) 50)
        (= (Requiere Segador Gas) 50)
        
        ; Investigaciones
        (EsTipoI impulsor_segador Impulsor_Segador)
        (= (InvestigacionRequiere Impulsor_Segador Mineral) 50)
        (= (InvestigacionRequiere Impulsor_Segador Gas) 200)
        
        ; Hay Recursos en 0
        (= (HayRecurso Gas) 0)
        (= (HayRecurso Mineral) 0)
        (= (MaximoRecurso) 500)
    )
    
    (:goal (and
            ; Un marine a la izquierda
            (EnU marine1 l2_2)
            ; Un marine y un segador a la derecha
            (EnU marine2 l4_4)
            (EnU segador1 l4_4)
        )
    )

)