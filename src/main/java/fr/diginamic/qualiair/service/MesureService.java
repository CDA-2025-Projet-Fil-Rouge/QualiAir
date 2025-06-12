package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.repository.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesureService
{
    @Autowired
    MesureRepository repository;
}
