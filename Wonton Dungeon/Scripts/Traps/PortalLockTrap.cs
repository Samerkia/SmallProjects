using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PortalLockTrap : Interact
{
    public override void OnInteract()
    {
        if (inventory.getKeyCount() >= 3)
        {
            inventory.setKeyCount(inventory.getKeyCount() - 2);
            gameObject.SetActive(false);
        }
    }
}
