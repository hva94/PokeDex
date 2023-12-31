# PokeDex - Aplicación Android

## Descripción
PokeDex es una aplicación móvil para Android que te permite explorar y descubrir información sobre 
tus Pokémon favoritos.

## Requerimientos completados
1.0. Crear una vista que permita mostrar una imagen circular a partir de una URL, las letras 
iniciales de una cadena compuesta por una o varias palabras o un placeholder. Sólo considerar las 
primeras dos palabras de la cadena.

1.1. Si la url está vacía o no se puede recuperar la imagen, mostrar las iniciales.

1.2. Si no se puede mostrar la imagen y la cadena está vacía mostrar un placeholder.

1.3. "Si sólo se tiene una palabra, mostrar sólo una inicial."

1.4. Si no se puede mostrar la imagen y la primera palabra comienza con número o un carácter 
fuera del alfabeto, mostrar un placeholder.

1.5. Las iniciales se deben mostrar en mayúsculas.

1.6. Permitir definir un background y color para las iniciales.

1.7. Permitir definir un placeholder.

1.8. Si la vista está basada en Android View, permitir establecer el background, color del texto y 
el placeholder desde un XML.

2.0. "Utilizando la API pokeapi v2 consumir el endpoint pokemon/ con paginación de 25 elementos."

2.1. "Con la misma API obtener el detalle de cada pokémon y persistir al menos su id, nombre, imagen
sprite frontal por defecto, altura, peso, nombre de los tipos del pokémon. "

2.2. En una pantalla mostrar un listado vertical con el nombre y el sprite del Pokémon. 
Utilizar el componente de la actividad 1 para mostrar la imagen o la inicial del nombre.

2.3. Al llegar al final del listado obtener los siguientes 25 elementos.

2.4. La información debe estar disponible cuando el dispositivo no tenga conexión a internet.

2.5. Al dar clic sobre la imagen de un item mostrarla expandida sobre la misma pantalla o en una
pantalla independiente.

2.6. Al dar clic sobre un item de la lista abrir la pantalla detalle (ver actividad 3).

2.7. Permitir marcar/desmarcar un pokémon como favorito desde la pantalla principal.

3.0. Crear una pantalla en Java para mostrar el detalle con la información del pokémon.

3.1. Agregar un botón que permita marcar/desmarcar un favorito.