    package com.domain.controllers;
    import java.util.List;

    import org.modelmapper.ModelMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.Errors;
    import org.springframework.validation.ObjectError;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import com.domain.dto.ResponseData;
    import com.domain.dto.SearchDto;
    import com.domain.dto.SupplierDto;
    import com.domain.models.entities.Supplier;
    import com.domain.services.SupplierService;

    import jakarta.validation.Valid;

    @RestController
    @RequestMapping("/api/suppliers")
    public class SupplierControllers {

        @Autowired
        private SupplierService supplierService;

        @Autowired
        private ModelMapper modelMapper;

        @PostMapping
        public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierDto supplierDto, Errors errors) {
            ResponseData<Supplier> responseData = new ResponseData<>();
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessage().add(error.getDefaultMessage());
                }
                responseData.setStatus(false);
                responseData.setPayload(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            Supplier supplier = modelMapper.map(supplierDto, Supplier.class);


            responseData.setStatus(true);
            responseData.setPayload(supplierService.save(supplier));
            return ResponseEntity.ok(responseData);
        }

        @GetMapping
        public  Iterable<Supplier> findAll(){
            return supplierService.findAll();
        }

        @GetMapping("/{id}")
        public Supplier findOne(@PathVariable("id") Long id){
            return supplierService.findOne(id); 
        }

        @PutMapping
        public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierDto supplierDto, Errors errors) {
            ResponseData<Supplier> responseData = new ResponseData<>();
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessage().add(error.getDefaultMessage());
                }
                responseData.setStatus(false);
                responseData.setPayload(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }

            Supplier supplier = modelMapper.map(supplierDto, Supplier.class);


            responseData.setStatus(true);
            responseData.setPayload(supplierService.save(supplier));
            return ResponseEntity.ok(responseData);
        }

        @PostMapping("/search/byemail")
        public Supplier findByEmail(@RequestBody SearchDto searchDto) {
        return supplierService.findByEmail(searchDto.getSearchKey()); 
        }

        @PostMapping("/search/byname")
        public List<Supplier> findByName(@RequestBody SearchDto searchDto){
            return supplierService.findByName(searchDto.getSearchKey());
        }
        
        @PostMapping("/search/namestartwith")
        public List<Supplier> findByNameStartWith(@RequestBody SearchDto searchDto){
        return supplierService.findByNameStartWith(searchDto.getSearchKey());
        }

        @PostMapping("/search/nameoremail")
        public List<Supplier> findByNameOrEmail(@RequestBody SearchDto searchDto){
        return supplierService.findByNameOrEmail(searchDto.getSearchKey(), searchDto.getOtherSearchKey());
        }
 
    }
        