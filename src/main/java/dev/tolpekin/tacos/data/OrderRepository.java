package dev.tolpekin.tacos.data;

import dev.tolpekin.tacos.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
}
