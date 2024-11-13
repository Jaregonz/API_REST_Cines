package com.es.diecines.controller;

import com.es.diecines.dto.PeliculaDTO;
import com.es.diecines.error.BaseDeDatosException;
import com.es.diecines.error.ErrorGenerico;
import com.es.diecines.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    @Autowired
    private PeliculaService peliculaService;

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody PeliculaDTO peliculaDTO){
        if(peliculaDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            try{
                return new ResponseEntity<>(this.peliculaService.insert(peliculaDTO),HttpStatus.CREATED);
            }catch (BaseDeDatosException e){
                ErrorGenerico error = new ErrorGenerico(
                    e.getMessage(),
                    "POST : localhost:8080/peliculas/"
            );
                return new ResponseEntity<ErrorGenerico>(error,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @GetMapping("/")
    public List<PeliculaDTO> getAll(){
        return this.peliculaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String id
    ) {
        try {
            // 1 Comprobar que el id no viene vacío
            if (id == null || id.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            // 2 Si no viene vacio, llamo al Service
            PeliculaDTO p = peliculaService.getById(id);

            // 3 Compruebo la validez de p para devolver una respuesta
            if(p == null) {
                ResponseEntity<ErrorGenerico> respuesta =
                        new ResponseEntity<>(
                                new ErrorGenerico("Pelicula no encontrada", "localhost:8080/peliculas/{id}"),
                                HttpStatus.NOT_FOUND);
                return respuesta;
            } else {
                ResponseEntity<PeliculaDTO> respuesta = new ResponseEntity<PeliculaDTO>(
                        p, HttpStatus.OK
                );
                return respuesta;
            }
        } catch (NumberFormatException e) {
            ErrorGenerico error = new ErrorGenerico(
                    e.getMessage(),
                    "localhost:8080/peliculas/"+id
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (BaseDeDatosException e) {
            ErrorGenerico error = new ErrorGenerico(
                    e.getMessage(),
                    "localhost:8080/peliculas/"+id
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public PeliculaDTO update(@RequestBody PeliculaDTO peliculaDTO, @PathVariable String id){
        if (id == null || id.isEmpty()){
            return null;
        }
        return this.peliculaService.update(peliculaDTO, id);
    }
    @DeleteMapping("/{id}")
    public PeliculaDTO delete(@PathVariable String id){

        if (id == null || id.isEmpty()){
            return null;
        }
        return this.peliculaService.delete(id);
    }
    @GetMapping("/rating/{minRating}")
    public List<PeliculaDTO> getByMinRate(@PathVariable String minRating){
        if (minRating == null || minRating.isEmpty()){
            return null;
        }
        return this.peliculaService.getByMinRate(minRating);
    }

}
