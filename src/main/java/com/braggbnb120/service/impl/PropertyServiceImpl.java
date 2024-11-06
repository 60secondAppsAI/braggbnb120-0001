package com.braggbnb120.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbnb120.dao.GenericDAO;
import com.braggbnb120.service.GenericService;
import com.braggbnb120.service.impl.GenericServiceImpl;
import com.braggbnb120.dao.PropertyDAO;
import com.braggbnb120.domain.Property;
import com.braggbnb120.dto.PropertyDTO;
import com.braggbnb120.dto.PropertySearchDTO;
import com.braggbnb120.dto.PropertyPageDTO;
import com.braggbnb120.dto.PropertyConvertCriteriaDTO;
import com.braggbnb120.dto.common.RequestDTO;
import com.braggbnb120.dto.common.ResultDTO;
import com.braggbnb120.service.PropertyService;
import com.braggbnb120.util.ControllerUtils;





@Service
public class PropertyServiceImpl extends GenericServiceImpl<Property, Integer> implements PropertyService {

    private final static Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

	@Autowired
	PropertyDAO propertyDao;

	


	@Override
	public GenericDAO<Property, Integer> getDAO() {
		return (GenericDAO<Property, Integer>) propertyDao;
	}
	
	public List<Property> findAll () {
		List<Property> propertys = propertyDao.findAll();
		
		return propertys;	
		
	}

	public ResultDTO addProperty(PropertyDTO propertyDTO, RequestDTO requestDTO) {

		Property property = new Property();

		property.setPropertyId(propertyDTO.getPropertyId());


		property.setTitle(propertyDTO.getTitle());


		property.setDescription(propertyDTO.getDescription());


		property.setLocation(propertyDTO.getLocation());


		property.setPricePerNight(propertyDTO.getPricePerNight());


		property.setMaxGuests(propertyDTO.getMaxGuests());


		property.setAvailability(propertyDTO.getAvailability());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		property = propertyDao.save(property);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Property> getAllPropertys(Pageable pageable) {
		return propertyDao.findAll(pageable);
	}

	public Page<Property> getAllPropertys(Specification<Property> spec, Pageable pageable) {
		return propertyDao.findAll(spec, pageable);
	}

	public ResponseEntity<PropertyPageDTO> getPropertys(PropertySearchDTO propertySearchDTO) {
	
			Integer propertyId = propertySearchDTO.getPropertyId(); 
 			String title = propertySearchDTO.getTitle(); 
 			String description = propertySearchDTO.getDescription(); 
 			String location = propertySearchDTO.getLocation(); 
   			String availability = propertySearchDTO.getAvailability(); 
 			String sortBy = propertySearchDTO.getSortBy();
			String sortOrder = propertySearchDTO.getSortOrder();
			String searchQuery = propertySearchDTO.getSearchQuery();
			Integer page = propertySearchDTO.getPage();
			Integer size = propertySearchDTO.getSize();

	        Specification<Property> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, propertyId, "propertyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, location, "location"); 
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, availability, "availability"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("location")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("availability")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Property> propertys = this.getAllPropertys(spec, pageable);
		
		//System.out.println(String.valueOf(propertys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(propertys.getTotalPages()));
		
		List<Property> propertysList = propertys.getContent();
		
		PropertyConvertCriteriaDTO convertCriteria = new PropertyConvertCriteriaDTO();
		List<PropertyDTO> propertyDTOs = this.convertPropertysToPropertyDTOs(propertysList,convertCriteria);
		
		PropertyPageDTO propertyPageDTO = new PropertyPageDTO();
		propertyPageDTO.setPropertys(propertyDTOs);
		propertyPageDTO.setTotalElements(propertys.getTotalElements());
		return ResponseEntity.ok(propertyPageDTO);
	}

	public List<PropertyDTO> convertPropertysToPropertyDTOs(List<Property> propertys, PropertyConvertCriteriaDTO convertCriteria) {
		
		List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
		
		for (Property property : propertys) {
			propertyDTOs.add(convertPropertyToPropertyDTO(property,convertCriteria));
		}
		
		return propertyDTOs;

	}
	
	public PropertyDTO convertPropertyToPropertyDTO(Property property, PropertyConvertCriteriaDTO convertCriteria) {
		
		PropertyDTO propertyDTO = new PropertyDTO();
		
		propertyDTO.setPropertyId(property.getPropertyId());

	
		propertyDTO.setTitle(property.getTitle());

	
		propertyDTO.setDescription(property.getDescription());

	
		propertyDTO.setLocation(property.getLocation());

	
		propertyDTO.setPricePerNight(property.getPricePerNight());

	
		propertyDTO.setMaxGuests(property.getMaxGuests());

	
		propertyDTO.setAvailability(property.getAvailability());

	

		
		return propertyDTO;
	}

	public ResultDTO updateProperty(PropertyDTO propertyDTO, RequestDTO requestDTO) {
		
		Property property = propertyDao.getById(propertyDTO.getPropertyId());

		property.setPropertyId(ControllerUtils.setValue(property.getPropertyId(), propertyDTO.getPropertyId()));

		property.setTitle(ControllerUtils.setValue(property.getTitle(), propertyDTO.getTitle()));

		property.setDescription(ControllerUtils.setValue(property.getDescription(), propertyDTO.getDescription()));

		property.setLocation(ControllerUtils.setValue(property.getLocation(), propertyDTO.getLocation()));

		property.setPricePerNight(ControllerUtils.setValue(property.getPricePerNight(), propertyDTO.getPricePerNight()));

		property.setMaxGuests(ControllerUtils.setValue(property.getMaxGuests(), propertyDTO.getMaxGuests()));

		property.setAvailability(ControllerUtils.setValue(property.getAvailability(), propertyDTO.getAvailability()));



        property = propertyDao.save(property);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PropertyDTO getPropertyDTOById(Integer propertyId) {
	
		Property property = propertyDao.getById(propertyId);
			
		
		PropertyConvertCriteriaDTO convertCriteria = new PropertyConvertCriteriaDTO();
		return(this.convertPropertyToPropertyDTO(property,convertCriteria));
	}







}
