package com.claro.projeto.model.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.claro.projeto.util.enums.Status;
import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;



public class VirtualMachineServiceTest {

    @InjectMocks
    private VirtualMachineService virtualMachineService;

    @Mock
    private VirtualMachineRepository virtualMachineRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveVm() {
        VirtualMachine machine = new VirtualMachine();
        machine.setMemory(512);
        machine.setCpu(4);
        machine.setDisplayName("VM-2");

        when(virtualMachineRepository.save(any(VirtualMachine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VirtualMachine result = virtualMachineService.save(machine);

        assertEquals(512, result.getMemory());
        assertEquals("VM-2", result.getDisplayName());
        assertEquals(4, result.getCpu());

        verify(virtualMachineRepository, times(1)).save(machine);
    }


    @Test
    public void testFindByIDExists() {
        VirtualMachine machine = new VirtualMachine();
        machine.setId(1L);

        when(virtualMachineRepository.findById(1L)).thenReturn(Optional.of(machine));

        VirtualMachine result = virtualMachineService.findByID(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindByIDNotFound() {
        when(virtualMachineRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaExeception.class, () -> {
            virtualMachineService.findByID(2L);
        });
    }

    @Test
    public void testDelete() {
        VirtualMachine machine = new VirtualMachine();
        machine.setId(1L);
        machine.setHabilitado(true);

        when(virtualMachineRepository.findById(1L)).thenReturn(Optional.of(machine));

        virtualMachineService.delete(1L);

        assertFalse(machine.getHabilitado());
        verify(virtualMachineRepository).save(machine);
    }

    @Test
    public void testUpdate() {
        VirtualMachine existingVirtualMachine = new VirtualMachine();
        existingVirtualMachine.setId(1L);
        existingVirtualMachine.setVersion(1L);
        existingVirtualMachine.setCpu(2);
        existingVirtualMachine.setMemory(512);
        existingVirtualMachine.setStatus(Status.RUNNING);
        existingVirtualMachine.setDisplayName("VM-3");

        VirtualMachine updatedVirtualMachine = new VirtualMachine();
        updatedVirtualMachine.setCpu(5);
        updatedVirtualMachine.setMemory(256);
        updatedVirtualMachine.setStatus(Status.RUNNING);
        updatedVirtualMachine.setDisplayName("VM-4");

        when(virtualMachineRepository.findById(1L)).thenReturn(Optional.of(existingVirtualMachine));
        when(virtualMachineRepository.save(any(VirtualMachine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        virtualMachineService.update(1L, updatedVirtualMachine);

        assertEquals(256, existingVirtualMachine.getMemory());
        assertEquals(5, existingVirtualMachine.getCpu());
        assertEquals(Status.RUNNING, existingVirtualMachine.getStatus());
        assertEquals("VM-4", existingVirtualMachine.getDisplayName());

        assertEquals(2L, existingVirtualMachine.getVersion());
        assertNotNull(existingVirtualMachine.getLastModificationDate());

        verify(virtualMachineRepository).save(existingVirtualMachine);
    }

}
    