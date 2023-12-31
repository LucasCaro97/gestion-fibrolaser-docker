package com.gestion.fibrolaser.servicios;

import com.gestion.fibrolaser.entidades.EstadoPedido;
import com.gestion.fibrolaser.entidades.Pedido;
import com.gestion.fibrolaser.entidades.Usuario;
import com.gestion.fibrolaser.repositorios.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServicio {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public void create(Pedido pedidoDto){
        Pedido pedido = new Pedido();
        pedido.setCliente(pedidoDto.getCliente());
        pedido.setDescripcion(pedidoDto.getDescripcion());
        pedido.setFechaIngreso(LocalDate.now());
        pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedido.setSenia(pedidoDto.getSenia());
        pedido.setUsuario(pedidoDto.getUsuario());
        pedidoRepository.save(pedido);

    }

    @Transactional
    public void update(Pedido pedidoDto){

        Pedido pedido = pedidoRepository.findById(pedidoDto.getId()).get();
        pedido.setCliente(pedidoDto.getCliente());
        pedido.setDescripcion(pedidoDto.getDescripcion());
        pedido.setFechaIngreso(LocalDate.now());
        pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedido.setSenia(pedidoDto.getSenia());
        pedido.setUsuario(pedidoDto.getUsuario());
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> getAll(Integer idEstado, Integer cliente){

        Sort sort = Sort.by("fechaEntrega").ascending();

        if(idEstado != null && cliente != null){
            return pedidoRepository.searchByEstadoPedidoAndCliente(idEstado, cliente);
        }else if ( cliente == null && idEstado != null){
            return pedidoRepository.searchByEstadoPedido(idEstado);
        }else if (idEstado == null && cliente != null) {
            return  pedidoRepository.searchByCliente(cliente);
        }else{
            return pedidoRepository.findAll(sort);
        }

    }

    @Transactional(readOnly = true)
    public List<Pedido> getByUser(Integer id){
        return pedidoRepository.searchByUsuarioNativeQUery(id);

    }

    @Transactional(readOnly = true)
    public List<Pedido> getByUserObject(Usuario usuario){
        return pedidoRepository.findByUsuario(usuario);

    }

//    @Transactional(readOnly = true)
//    public List<Pedido> getByEstadoPedidoç(EstadoPedido pedido){
//        return pedidoRepository.searchByEstadoPedido(pedido.getId());
//    }


    @Transactional
    public Pedido getById(Integer id){ return pedidoRepository.findById(id).get(); }

    @Transactional
    public void deleteById(Integer id) { pedidoRepository.deleteById(id);}

}

