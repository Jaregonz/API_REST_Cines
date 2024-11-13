package com.es.diecines.services;

import com.es.diecines.dto.PeliculaDTO;
import com.es.diecines.model.Pelicula;
import com.es.diecines.model.Sesion;
import com.es.diecines.repository.PeliculaRepository;
import com.es.diecines.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private SesionRepository sesionRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }
    public PeliculaDTO insert(PeliculaDTO peliculaDTO) {
        if(peliculaDTO == null){
            return null;
        }else{
            Pelicula pelicula = new Pelicula();
            pelicula.setTitle(peliculaDTO.getTitle());
            pelicula.setDirector(peliculaDTO.getDirector());
            pelicula.setTime(peliculaDTO.getTime());
            pelicula.setTrailer(peliculaDTO.getTrailer());
            pelicula.setPosterImage(peliculaDTO.getPosterImage());
            pelicula.setScreenshot(peliculaDTO.getScreenshot());
            pelicula.setSynopsis(peliculaDTO.getSynopsis());
            pelicula.setRating(peliculaDTO.getRating());
            pelicula = peliculaRepository.save(pelicula);
            return mapToDTO(pelicula);
        }

    }
    public List<PeliculaDTO> getAll(){
        List<PeliculaDTO> peliculaDTOS = new ArrayList<>();
        List<Pelicula> peliculas = this.peliculaRepository.findAll();
        for (int i = 0; i < peliculas.size(); i++) {
            peliculaDTOS.add(mapToDTO(peliculas.get(i)));
        }
        return peliculaDTOS ;
    }
    public PeliculaDTO getById(String id){
        try{
            Long idLong = Long.parseLong(id);
            return mapToDTO(this.peliculaRepository.findById((idLong)).get());
        } catch (NumberFormatException e) {
            System.out.println("No es una id Válida");
            return null;
        }
    }

    public PeliculaDTO update(PeliculaDTO peliculaDTO, String id){
        try{
            Long idLong = Long.parseLong(id);
            Optional<Pelicula> p = this.peliculaRepository.findById(idLong);
            if(p.isPresent()){
                Pelicula pelicula = p.get();
                pelicula.setDirector(peliculaDTO.getDirector());
                pelicula.setPosterImage(peliculaDTO.getPosterImage());
                pelicula.setRating(peliculaDTO.getRating());
                pelicula.setScreenshot(peliculaDTO.getScreenshot());
                pelicula.setSynopsis(peliculaDTO.getSynopsis());
                pelicula.setTime(peliculaDTO.getTime());
                pelicula.setTitle(peliculaDTO.getTitle());
                pelicula.setTrailer(peliculaDTO.getTrailer());
                return mapToDTO(this.peliculaRepository.save(pelicula));
            }

        } catch (Exception e) {
            System.out.println("Error inesperado");
        }
        return null;
    }
    public PeliculaDTO delete(String id){
        try{
            Long idLong = Long.parseLong(id);
            Optional<Pelicula> p = this.peliculaRepository.findById(idLong);
            if(p.isPresent()){
                List<Sesion> sesiones = this.sesionRepository.findAll();

                for(Sesion s: sesiones){
                    if (Objects.equals(s.getPelicula().getId(), idLong)){
                        this.sesionRepository.delete(s);
                    }
                }

                this.peliculaRepository.delete(p.get());
                return mapToDTO(p.get());
            }else{
                System.out.println("La pelicula con el id "+id+" ya no existe");
            }

        }catch(Exception e){
            System.out.println("El id no es válido");
            System.out.println(e.getCause());
            return null;
        }
        return null;
    }
    public List<PeliculaDTO> getByMinRate(String minRating){
        List<Pelicula> allPeliculas = this.peliculaRepository.findAll();
        List<PeliculaDTO> allPeliculasByMinRateDTOs = new ArrayList<>();
        for(Pelicula p: allPeliculas){
            if (p.getRating() >= (Double.parseDouble(minRating))){
                allPeliculasByMinRateDTOs.add(mapToDTO(p));
            }
        }
        return allPeliculasByMinRateDTOs;
    }

    private PeliculaDTO mapToDTO(Pelicula pelicula) {
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setId(pelicula.getId());
        peliculaDTO.setTitle(pelicula.getTitle());
        peliculaDTO.setDirector(pelicula.getDirector());
        peliculaDTO.setTime(pelicula.getTime());
        peliculaDTO.setTrailer(pelicula.getTrailer());
        peliculaDTO.setPosterImage(pelicula.getPosterImage());
        peliculaDTO.setScreenshot(pelicula.getScreenshot());
        peliculaDTO.setPosterImage(pelicula.getPosterImage());
        peliculaDTO.setSynopsis(pelicula.getSynopsis());
        peliculaDTO.setRating(pelicula.getRating());
        return peliculaDTO;
    }
}
