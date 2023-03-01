using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Health : Interact
{
    private int BatteryCount;

    // Increases Key Count and Removes from world
    public override void OnInteract()
    {
        gameObject.SetActive(false);
        inventory.setHealth(inventory.getHealth() + 25);
    }
}
