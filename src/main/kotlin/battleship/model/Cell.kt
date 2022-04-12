package battleship.model

import battleship.model.position.Position


class Cell private constructor(val pos : Position)
{

}

class ShipCell() : Cell()
{

}


