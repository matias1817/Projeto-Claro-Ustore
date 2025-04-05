package com.claro.projeto.api.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.claro.projeto.model.machine.VirtualMachine;
import com.claro.projeto.model.machine.VirtualMachineService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VirtualMachineControllerTest {

    @InjectMocks
    private VirtualMachineController virtualMachineController;

    @Mock
    private VirtualMachineService virtualMachineService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        VirtualMachineRequest request = new VirtualMachineRequest();
        request.setCpu(1);
        request.setMemory(512);
        request.setDisplayName("VM-2");
        request.setStatus("RUNNING");

        VirtualMachine mockVirtualMachine = new VirtualMachine();
        mockVirtualMachine.setId(1L);
        mockVirtualMachine.setDisplayName("VM-2");

        when(virtualMachineService.save(any(VirtualMachine.class))).thenReturn(mockVirtualMachine);

        ResponseEntity<VirtualMachineResource> response = virtualMachineController.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VM-2", response.getBody().displayName());
    }

    @Test
    public void testFindByID() {
        VirtualMachine mockVirtualMachine = new VirtualMachine();
        mockVirtualMachine.setId(1L);
        mockVirtualMachine.setDisplayName("VM-4");

        when(virtualMachineService.findByID(1L)).thenReturn(mockVirtualMachine);

        ResponseEntity<VirtualMachineResource> response = virtualMachineController.findByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VM-4", response.getBody().displayName());
    }

    @Test
    public void testUpdate() {
        VirtualMachineRequest request = new VirtualMachineRequest();
        request.setCpu(3);
        request.setDisplayName("VM-1");
        request.setStatus("RUNNING");

        doNothing().when(virtualMachineService).update(eq(1L), any(VirtualMachine.class));

        ResponseEntity<VirtualMachine> response = virtualMachineController.update(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(virtualMachineService, times(1)).update(eq(1L), any(VirtualMachine.class));

    }

    @Test
    public void testDelete() {
        doNothing().when(virtualMachineService).delete(1L);

        ResponseEntity<Void> response = virtualMachineController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(virtualMachineService, times(1)).delete(1L);
    }
}