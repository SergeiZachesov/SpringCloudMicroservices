package com.microservices.deposit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.deposit.DepositApplication;
import com.microservices.deposit.config.SpringBootH2TestConfig;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.entity.Deposit;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.AccountResponseDto;
import com.microservices.deposit.rest.AccountServiceClient;
import com.microservices.deposit.rest.BillServiceClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static com.microservices.deposit.DepositTestData.createAccountResponseDto;
import static com.microservices.deposit.DepositTestData.createBillResponseDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DepositApplication.class, SpringBootH2TestConfig.class})
public class DepositControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private BillServiceClient billServiceClient;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String REQUEST = "{\n" +
            "    \"billId\": 1,\n" +
            "    \"amount\": 3000\n" +
            "}";

    @Test
    public void createDeposit() throws Exception {
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong()))
                .thenReturn(createBillResponseDTO());
        AccountResponseDto accountResponseDto = createAccountResponseDto();
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong()))
                .thenReturn(accountResponseDto);

        MvcResult result = mockMvc.perform(post("/deposits")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(content, DepositResponseDto.class);

        List<Deposit> deposits = depositRepository.findDepositsByEmail(accountResponseDto.getEmail());

        Assertions.assertThat(depositResponseDto.getMail()).isEqualTo(deposits.get(0).getEmail());
        Assertions.assertThat(depositResponseDto.getAmount()).isEqualTo(BigDecimal.valueOf(3000));
    }
}