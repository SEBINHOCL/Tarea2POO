package com.example.stage4;

/**
 * Clase abstracta base para componentes como Publisher y Subscriber.
 * Contiene nombre del componente y del tópico asociado.
 */
public class Component {

   /**
    * Constructor protegido para prevenir instancias sin nombre.
    */
   protected Component () {}

   /**
    * Constructor con parámetros.
    *
    * @param componentName Nombre del componente.
    * @param topicName Nombre del tópico asociado.
    */
   public Component(String componentName, String topicName){
      name = componentName;
      this.topicName = topicName;
   }

   /**
    * Retorna el nombre del componente.
    *
    * @return Nombre del componente.
    */
   public String getName(){
      return name;
   }

   /**
    * Retorna el nombre del tópico asociado.
    *
    * @return Nombre del tópico.
    */
   public String getTopicName(){
      return topicName;
   }

   protected String name;
   protected String topicName;
}
