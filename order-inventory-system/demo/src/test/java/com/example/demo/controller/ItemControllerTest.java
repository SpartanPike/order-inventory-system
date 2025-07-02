package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemCriteriaRepository;
import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemCriteriaRepository itemCriteriaRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void getAllItems_shouldReturnListOfItemDTOs() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setName("Notebook");
        item.setQuantity(10);
        item.setPrice(199.0);

        ItemDTO dto = new ItemDTO();
        dto.setId(1L);
        dto.setName("Notebook");
        dto.setQuantity(10);
        dto.setPrice(199.0);

        when(itemRepository.findAll()).thenReturn(List.of(item));
        when(modelMapper.map(item, ItemDTO.class)).thenReturn(dto);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Notebook")))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[0].price", is(199.0)));
    }
}
